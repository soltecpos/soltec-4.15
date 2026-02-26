// ============================================
// Module: Tickets
// ============================================

const TicketsModule = {

    async render() {
        const content = document.getElementById('content-area');
        content.innerHTML = `
            <div class="animate-in">
                <div class="toolbar">
                    <button class="btn btn-primary tab-active" id="tab-open" onclick="TicketsModule.showOpen()">
                        Tickets Abiertos
                    </button>
                    <button class="btn btn-secondary" id="tab-closed" onclick="TicketsModule.showClosed()">
                        Tickets Cerrados
                    </button>
                    <div style="flex:1"></div>
                    <div class="form-group" style="margin-bottom:0">
                        <input type="date" id="tickets-date" class="form-input" style="width:160px" onchange="TicketsModule.showClosed()">
                    </div>
                </div>
                <div id="tickets-content">
                    <div class="loading-state"><div class="spinner"></div></div>
                </div>
            </div>
        `;

        const today = new Date().toISOString().split('T')[0];
        document.getElementById('tickets-date').value = today;
        await this.showOpen();
    },

    async showOpen() {
        document.getElementById('tab-open').className = 'btn btn-primary';
        document.getElementById('tab-closed').className = 'btn btn-secondary';

        try {
            const tickets = await API.getOpenTickets();
            const container = document.getElementById('tickets-content');

            if (!tickets.length) {
                container.innerHTML = '<div class="empty-state"><h3>No hay tickets abiertos</h3><p>Los pedidos activos aparecerán aquí</p></div>';
                return;
            }

            container.innerHTML = `
                <div class="grid-3">
                    ${tickets.map(t => `
                        <div class="card" style="cursor:pointer" onclick="TicketsModule.showDetail('${t.id}')">
                            <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:8px">
                                <span style="font-weight:700;font-size:1.1rem">${this.esc(t.name)}</span>
                                <span class="tag tag-orange">Abierto</span>
                            </div>
                            <div style="font-size:0.82rem;color:var(--text-muted)">
                                ${t.appuser ? 'Usuario: ' + this.esc(t.appuser) : ''}
                                ${t.pickupid ? ' · Pickup: #' + t.pickupid : ''}
                            </div>
                            ${t.locked ? '<div style="margin-top:6px"><span class="tag tag-red">Bloqueado</span></div>' : ''}
                        </div>
                    `).join('')}
                </div>
            `;
        } catch (e) {
            document.getElementById('tickets-content').innerHTML =
                '<div class="empty-state"><h3>Error cargando tickets</h3></div>';
        }
    },

    async showClosed() {
        document.getElementById('tab-open').className = 'btn btn-secondary';
        document.getElementById('tab-closed').className = 'btn btn-primary';

        const date = document.getElementById('tickets-date').value;

        try {
            const tickets = await API.getClosedTickets(date);
            const container = document.getElementById('tickets-content');

            if (!tickets.length) {
                container.innerHTML = '<div class="empty-state"><h3>No hay tickets cerrados</h3></div>';
                return;
            }

            container.innerHTML = `
                <div class="card">
                    <div style="overflow-x:auto">
                        <table class="data-table">
                            <thead><tr>
                                <th>#</th><th>ID</th><th>Tipo</th><th>Fecha</th><th>Estado</th>
                            </tr></thead>
                            <tbody>
                                ${tickets.map(t => `
                                    <tr style="cursor:pointer" onclick="TicketsModule.showDetail('${t.id}')">
                                        <td>${t.ticketid}</td>
                                        <td>${t.id?.substring(0, 8)}</td>
                                        <td>${t.tickettype === 0 ? 'Venta' : t.tickettype === 1 ? 'Devolución' : 'Otro'}</td>
                                        <td>${t.date ? new Date(t.date).toLocaleString('es-CO') : '--'}</td>
                                        <td><span class="tag tag-blue">Cerrado</span></td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
        } catch (e) {
            document.getElementById('tickets-content').innerHTML =
                '<div class="empty-state"><h3>Error cargando tickets</h3></div>';
        }
    },

    async showDetail(id) {
        try {
            const ticket = await API.getTicket(id);
            console.log("== TICKET DEV ==", ticket);
            const overlay = document.createElement('div');
            overlay.className = 'modal-overlay';
            overlay.id = 'ticket-modal';

            let linesHtml = '';
            if (ticket.lines && ticket.lines.length) {
                const total = ticket.lines.reduce((s, l) => s + (l.total || 0), 0);
                linesHtml = `
                    <table class="data-table" style="margin-top:12px">
                        <thead><tr><th>Producto</th><th>Cant.</th><th>Precio</th><th>Total</th></tr></thead>
                        <tbody>
                            ${ticket.lines.map(l => `
                                <tr>
                                    <td>${this.esc(l.productName || l.productId?.substring(0, 8))}</td>
                                    <td>${l.units}</td>
                                    <td>$${(l.price || 0).toLocaleString('es-CO')}</td>
                                    <td style="font-weight:600">$${(l.total || 0).toLocaleString('es-CO')}</td>
                                </tr>
                            `).join('')}
                            <tr style="border-top:2px solid var(--accent-blue)">
                                <td colspan="3" style="font-weight:700;text-align:right">TOTAL</td>
                                <td style="font-weight:700;color:var(--accent-green);font-size:1.1rem">$${total.toLocaleString('es-CO')}</td>
                            </tr>
                        </tbody>
                    </table>
                `;
            }
            let kitchenBtnHtml = '';
            if (ticket.type === 'open' && ticket.lines && ticket.lines.length) {
                // Check if there's at least one line not sent THAT HAS A PRINTER
                const hasPrinterItems = ticket.lines.some(l => l.printer);
                const unsent = ticket.lines.filter(l => l.printer && l.sendstatus !== 'Yes');

                if (hasPrinterItems) {
                    if (unsent.length > 0) {
                        kitchenBtnHtml = `
                            <button class="btn btn-primary" onclick="TicketsModule.sendToKitchen('${ticket.id}')" style="margin-top:16px; width:100%; justify-content:center; background-color: var(--accent-orange); color: #fff;">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width:18px;height:18px;margin-right:8px"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
                                Enviar a Cocina (${unsent.length} items)
                            </button>
                        `;
                    } else {
                        kitchenBtnHtml = `
                            <div style="margin-top:16px; width:100%; text-align:center; padding: 10px; background: rgba(16, 185, 129, 0.1); color: var(--accent-green); border-radius: 6px;">
                                Todo ha sido enviado a cocina
                            </div>
                        `;
                    }
                }
            }

            overlay.innerHTML = `
                <div class="modal" style="max-width:600px">
                    <div class="modal-header">
                        <h2>Ticket ${ticket.ticketid ? '#' + ticket.ticketid : ticket.name || ticket.id?.substring(0, 8)}</h2>
                        <button class="modal-close" onclick="document.getElementById('ticket-modal').remove()">✕</button>
                    </div>
                    <div>
                        <span class="tag ${ticket.type === 'open' ? 'tag-orange' : 'tag-blue'}">${ticket.type === 'open' ? 'Abierto' : 'Cerrado'}</span>
                        ${ticket.date ? '<span style="margin-left:12px;color:var(--text-muted);font-size:0.82rem">' + new Date(ticket.date).toLocaleString('es-CO') + '</span>' : ''}
                    </div>
                    ${linesHtml}
                    ${kitchenBtnHtml}
                </div>
            `;
            document.body.appendChild(overlay);
            overlay.onclick = (e) => { if (e.target === overlay) overlay.remove(); };
        } catch (e) { alert('Error cargando detalle'); }
    },

    async sendToKitchen(id) {
        try {
            await API.request(`/api/tickets/${id}/kitchen`, { method: 'POST' });
            // Cierra el modal y refresca
            const modal = document.getElementById('ticket-modal');
            if (modal) modal.remove();
            this.showDetail(id); // Vuelve a abrir para ver el estado actualizado
            // Tambien puedes llamar a KitchenModule.loadOrders() si quisieras, pero el dashboard es por modulos.
            alert('¡Enviado a cocina!');
        } catch (e) {
            alert('Error enviando a cocina.');
        }
    },

    esc(str) { return str ? String(str).replace(/</g, '&lt;').replace(/>/g, '&gt;') : ''; }
};
