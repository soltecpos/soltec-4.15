// ============================================
// Module: Kitchen Display
// ============================================

const KitchenModule = {
    refreshTimer: null,
    seenItems: new Set(), // Tracks which item IDs have been "acknowledged" so they stop blinking

    async render() {
        const content = document.getElementById('content-area');
        content.innerHTML = `
            <div class="animate-in">
                <div class="toolbar">
                    <div style="flex:1">
                        <h3 style="color:var(--text-secondary);font-weight:500">
                            <span style="display:inline-block;width:10px;height:10px;border-radius:50%;background:var(--accent-green);margin-right:8px;animation:pulse-dot 2s infinite"></span>
                            Pedidos en tiempo real
                        </h3>
                    </div>
                    <button class="btn btn-secondary" onclick="KitchenModule.loadOrders()">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width:16px;height:16px"><polyline points="23 4 23 10 17 10"/><path d="M20.49 15a9 9 0 1 1-2.12-9.36L23 10"/></svg>
                        Refrescar
                    </button>
                </div>
                <div class="kitchen-grid" id="kitchen-grid" style="grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));">
                    <div class="loading-state"><div class="spinner"></div></div>
                </div>
            </div>
        `;

        await this.loadOrders();
        this.startAutoRefresh();
    },

    async loadOrders() {
        try {
            const orders = await API.getPendingOrders();
            const grid = document.getElementById('kitchen-grid');
            const badge = document.getElementById('kitchen-badge');

            if (badge) {
                badge.textContent = orders.length;
                badge.style.display = orders.length > 0 ? 'inline-flex' : 'none';
            }

            if (orders.length === 0) {
                grid.innerHTML = `
                    <div class="empty-state" style="grid-column:1/-1">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                            <circle cx="12" cy="12" r="10"/>
                            <path d="M8 12l2 2 4-4"/>
                        </svg>
                        <h3>Sin pedidos pendientes</h3>
                        <p>Los nuevos items confirmados aparecerán aquí automáticamente</p>
                    </div>
                `;
                return;
            }

            // Group orders by table name
            const groupedOrders = {};
            orders.forEach(order => {
                // Parse "Yu - (06:36 522)" into "Yu"
                let tableInfo = order.tableName || 'Para Llevar / Sin Mesa';
                if (tableInfo.includes(' - ')) {
                    tableInfo = tableInfo.split(' - ')[0].trim();
                }

                if (!groupedOrders[tableInfo]) {
                    groupedOrders[tableInfo] = { items: [], waiterInfo: order.waiterName || 'N/A', ticketId: order.ticketId };
                }
                groupedOrders[tableInfo].items.push(order);
            });

            // Make sure anything previously seen stays seen.
            // When orders are completely new, they are not in seenItems.

            // Generate HTML for each table card
            grid.innerHTML = Object.keys(groupedOrders).map(tableName => {
                const group = groupedOrders[tableName];

                // Helper to render each item row
                const itemsHtml = group.items.map(order => {
                    const isNew = !this.seenItems.has(order.id);
                    const blinkClass = isNew ? 'blink-kitchen' : '';
                    return `
                        <div class="kitchen-item-row ${blinkClass}" id="kitem-${order.id}" style="padding:8px; margin-bottom:8px;">
                            <div class="kitchen-item-info" style="flex:1">
                                <h4 style="margin:0; font-size:1rem;">${order.multiplier || 1}x ${this.esc(order.productName || 'Producto Desconocido')}</h4>
                            </div>
                            <div>
                                <button class="btn btn-primary btn-sm" onclick="KitchenModule.completeItem('${order.id}')" title="Marcar como listo">
                                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width:14px;height:14px;"><polyline points="20 6 9 17 4 12"/></svg>
                                    Listo
                                </button>
                            </div>
                        </div>
                    `;
                }).join('');

                const hasNewItems = group.items.some(o => !this.seenItems.has(o.id));
                const ackButtonHtml = hasNewItems ? `
                    <button class="btn btn-secondary btn-sm" onclick="KitchenModule.acknowledgeTable('${this.esc(tableName)}')" style="width:100%; margin-top:8px; justify-content:center; border-color:var(--accent-orange); color:var(--accent-orange);">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width:14px;height:14px;margin-right:4px"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                        Marcar como Visto
                    </button>
                ` : '';

                return `
                    <div class="card card-glass" style="display:flex; flex-direction:column; gap:12px;">
                        <div class="card-header" style="margin-bottom:0; padding-bottom:12px; border-bottom:1px solid var(--border-color);">
                            <div>
                                <span class="card-title" style="font-size:1.1rem; color:var(--accent-orange)">${this.esc(tableName)}</span>
                                <div class="card-subtitle" style="margin-top:4px;">Mesero: ${this.esc(group.waiterInfo)}</div>
                            </div>
                            <span class="tag tag-blue">Tk #${group.ticketId?.substring(0, 6)}</span>
                        </div>
                        <div style="flex:1;">
                            ${itemsHtml}
                        </div>
                        ${ackButtonHtml}
                    </div>
                `;
            }).join('');

        } catch (e) {
            document.getElementById('kitchen-grid').innerHTML =
                '<div class="empty-state" style="grid-column:1/-1"><h3>Error cargando pedidos</h3></div>';
        }
    },

    async completeItem(id) {
        try {
            await API.request(`/api/kitchen/${id}/complete`, { method: 'PUT' });
            this.seenItems.delete(id); // Clean up tracking
            this.loadOrders(); // Refresh instantly
        } catch (e) {
            console.error("Error completing item", e);
        }
    },

    async acknowledgeTable(tableName) {
        // Find all orders currently rendered for this table and mark them as seen
        try {
            const orders = await API.getPendingOrders();
            orders.forEach(order => {
                const tableInfo = order.tableName || 'Para Llevar / Sin Mesa';
                if (tableInfo === tableName) {
                    this.seenItems.add(order.id);
                }
            });
            this.loadOrders(); // Re-render to clear blinking effect and hide button
        } catch (e) {
            console.error("Error acknowledging table", e);
        }
    },

    startAutoRefresh() {
        this.stopAutoRefresh();
        this.refreshTimer = setInterval(() => this.loadOrders(), 5000);
    },

    stopAutoRefresh() {
        if (this.refreshTimer) {
            clearInterval(this.refreshTimer);
            this.refreshTimer = null;
        }
    },

    esc(str) { return str ? String(str).replace(/</g, '&lt;').replace(/>/g, '&gt;') : ''; }
};
