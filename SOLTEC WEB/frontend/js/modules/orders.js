// ============================================
// Module: Orders (Table Status)
// ============================================

const OrdersModule = {

    async render() {
        const content = document.getElementById('content-area');
        content.innerHTML = `
            <div class="animate-in">
                <div class="toolbar">
                    <select class="form-select" id="orders-floor" style="width:200px" onchange="OrdersModule.loadTables()">
                        <option value="">Todos los pisos</option>
                    </select>
                    <button class="btn btn-secondary" onclick="OrdersModule.loadTables()">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width:16px;height:16px"><polyline points="23 4 23 10 17 10"/><path d="M20.49 15a9 9 0 1 1-2.12-9.36L23 10"/></svg>
                        Refrescar
                    </button>
                </div>

                <div class="stats-grid" id="table-stats" style="margin-bottom:20px">
                    <div class="stat-card green">
                        <div class="stat-icon green">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M8 12l2 2 4-4"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Mesas Libres</div>
                            <div class="stat-value" id="tables-free">--</div>
                        </div>
                    </div>
                    <div class="stat-card orange">
                        <div class="stat-icon orange">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 6v6l4 2"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Mesas Ocupadas</div>
                            <div class="stat-value" id="tables-occupied">--</div>
                        </div>
                    </div>
                </div>

                <div class="tables-grid" id="tables-grid">
                    <div class="loading-state"><div class="spinner"></div></div>
                </div>
            </div>
        `;

        await this.loadFloors();
        await this.loadTables();
    },

    async loadFloors() {
        try {
            const floors = await API.getFloors();
            const select = document.getElementById('orders-floor');
            floors.forEach(f => {
                const opt = document.createElement('option');
                opt.value = f.id;
                opt.textContent = f.name;
                select.appendChild(opt);
            });
        } catch (e) { console.error(e); }
    },

    async loadTables() {
        try {
            const tables = await API.getTables();
            const floorId = document.getElementById('orders-floor').value;
            const filtered = floorId ? tables.filter(t => t.floorId === floorId) : tables;

            const free = filtered.filter(t => !t.occupied).length;
            const occupied = filtered.filter(t => t.occupied).length;
            document.getElementById('tables-free').textContent = free;
            document.getElementById('tables-occupied').textContent = occupied;

            const grid = document.getElementById('tables-grid');
            if (!filtered.length) {
                grid.innerHTML = '<div class="empty-state"><h3>No hay mesas configuradas</h3></div>';
                return;
            }

            grid.innerHTML = filtered.map(t => `
                <div class="table-card ${t.occupied ? 'occupied' : 'free'}" onclick="OrdersModule.showTableDetail('${t.id}', '${this.esc(t.name)}', ${t.occupied})">
                    <div class="table-name">${this.esc(t.name)}</div>
                    <div class="table-status">${t.occupied ? '● Ocupada' : '○ Libre'}</div>
                    ${t.waiter ? '<div style="font-size:0.72rem;color:var(--text-muted);margin-top:4px">' + this.esc(t.waiter) + '</div>' : ''}
                </div>
            `).join('');
        } catch (e) {
            document.getElementById('tables-grid').innerHTML =
                '<div class="empty-state"><h3>Error cargando mesas</h3></div>';
        }
    },

    showTableDetail(id, name, occupied) {
        if (occupied) {
            TicketsModule.showDetail(id);
        }
    },

    esc(str) { return str ? String(str).replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/'/g, '&#39;') : ''; }
};
