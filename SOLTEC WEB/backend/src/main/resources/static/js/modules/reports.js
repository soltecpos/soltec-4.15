// ============================================
// Module: Reports
// ============================================

const ReportsModule = {
    charts: {},

    async render() {
        const content = document.getElementById('content-area');
        content.innerHTML = `
            <div class="animate-in">
                <div class="toolbar">
                    <div class="form-group" style="margin-bottom:0">
                        <label class="form-label">Desde</label>
                        <input type="date" id="report-from" class="form-input" style="width:160px">
                    </div>
                    <div class="form-group" style="margin-bottom:0">
                        <label class="form-label">Hasta</label>
                        <input type="date" id="report-to" class="form-input" style="width:160px">
                    </div>
                    <button class="btn btn-primary" onclick="ReportsModule.loadRange()" style="margin-top:18px">
                        Consultar
                    </button>
                </div>

                <div class="stats-grid" id="sales-stats">
                    <div class="stat-card blue">
                        <div class="stat-icon blue">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Ventas del Día</div>
                            <div class="stat-value" id="total-sales">--</div>
                        </div>
                    </div>
                    <div class="stat-card orange">
                        <div class="stat-icon orange">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M15 5v2M15 11v2M15 17v2M5 5h14a2 2 0 0 1 2 2v3a2 2 0 0 0 0 4v3a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-3a2 2 0 0 0 0-4V7a2 2 0 0 1 2-2z"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Tickets</div>
                            <div class="stat-value" id="total-tickets">--</div>
                        </div>
                    </div>
                    <div class="stat-card green">
                        <div class="stat-icon green">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="2" y="4" width="20" height="16" rx="2"/><path d="M2 10h20"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Estado de Caja</div>
                            <div class="stat-value" id="cash-status">--</div>
                        </div>
                    </div>
                    <div class="stat-card purple">
                        <div class="stat-icon purple">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M18 20V10M12 20V4M6 20v-6"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Promedio / Ticket</div>
                            <div class="stat-value" id="avg-ticket">--</div>
                        </div>
                    </div>
                </div>

                <div class="grid-2">
                    <div class="card">
                        <div class="card-header">
                            <span class="card-title">Productos Más Vendidos</span>
                        </div>
                        <div class="chart-container">
                            <canvas id="chart-top-products"></canvas>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <span class="card-title">Formas de Pago</span>
                        </div>
                        <div class="chart-container">
                            <canvas id="chart-payments"></canvas>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <span class="card-title">Historial de Caja</span>
                    </div>
                    <div style="overflow-x:auto">
                        <table class="data-table" id="cash-history-table">
                            <thead><tr>
                                <th>Sesión</th><th>Host</th><th>Apertura</th><th>Cierre</th><th>Estado</th>
                            </tr></thead>
                            <tbody id="cash-history-body"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        `;

        // Set default dates
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('report-from').value = today;
        document.getElementById('report-to').value = today;

        await this.loadData();
    },

    async loadData() {
        try {
            const [sales, topProducts, cash, cashHistory] = await Promise.all([
                API.salesToday(),
                API.topProducts(),
                API.currentCash(),
                API.cashHistory(10)
            ]);

            this.updateStats(sales, cash);
            this.renderTopProductsChart(topProducts);
            this.renderPaymentsChart(sales.paymentBreakdown);
            this.renderCashHistory(cashHistory);
        } catch (e) {
            console.error('Reports load error:', e);
            document.getElementById('total-sales').textContent = 'Error';
        }
    },

    async loadRange() {
        const from = document.getElementById('report-from').value;
        const to = document.getElementById('report-to').value;
        if (!from || !to) return;

        try {
            const [sales, topProducts] = await Promise.all([
                API.salesRange(from, to),
                API.topProducts(from, to)
            ]);
            this.updateStats(sales, null);
            this.renderTopProductsChart(topProducts);
            this.renderPaymentsChart(sales.paymentBreakdown);
        } catch (e) { console.error(e); }
    },

    updateStats(sales, cash) {
        const fmt = (n) => '$' + (n || 0).toLocaleString('es-CO', { minimumFractionDigits: 0 });
        document.getElementById('total-sales').textContent = fmt(sales.totalSales);
        document.getElementById('total-tickets').textContent = sales.totalTickets || 0;
        const avg = sales.totalTickets > 0 ? sales.totalSales / sales.totalTickets : 0;
        document.getElementById('avg-ticket').textContent = fmt(Math.round(avg));

        if (cash) {
            const el = document.getElementById('cash-status');
            el.textContent = cash.status === 'OPEN' ? 'Abierta' : 'Cerrada';
            el.style.color = cash.status === 'OPEN' ? 'var(--accent-green)' : 'var(--accent-red)';
        }
    },

    renderTopProductsChart(data) {
        if (this.charts.topProducts) this.charts.topProducts.destroy();
        const ctx = document.getElementById('chart-top-products');
        if (!ctx) return;

        const colors = ['#3b82f6', '#f97316', '#22c55e', '#a855f7', '#eab308', '#ef4444', '#06b6d4', '#ec4899', '#14b8a6', '#8b5cf6'];
        this.charts.topProducts = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.slice(0, 8).map(d => d.productName?.substring(0, 15) || '?'),
                datasets: [{
                    label: 'Ventas ($)',
                    data: data.slice(0, 8).map(d => d.totalSales),
                    backgroundColor: colors.slice(0, 8).map(c => c + '33'),
                    borderColor: colors.slice(0, 8),
                    borderWidth: 1,
                    borderRadius: 6,
                }]
            },
            options: {
                responsive: true, maintainAspectRatio: false,
                plugins: { legend: { display: false } },
                scales: {
                    x: { ticks: { color: '#94a3b8', font: { size: 11 } }, grid: { display: false } },
                    y: { ticks: { color: '#94a3b8' }, grid: { color: 'rgba(255,255,255,0.04)' } }
                }
            }
        });
    },

    renderPaymentsChart(breakdown) {
        if (this.charts.payments) this.charts.payments.destroy();
        const ctx = document.getElementById('chart-payments');
        if (!ctx || !breakdown) return;

        const labels = Object.keys(breakdown);
        const values = labels.map(k => breakdown[k].total);
        const colors = ['#3b82f6', '#f97316', '#22c55e', '#a855f7', '#eab308'];

        this.charts.payments = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: labels.map(l => l === 'cash' ? 'Efectivo' : l === 'cashrefund' ? 'Devolución' : l),
                datasets: [{
                    data: values,
                    backgroundColor: colors.slice(0, labels.length),
                    borderWidth: 0,
                    hoverOffset: 8
                }]
            },
            options: {
                responsive: true, maintainAspectRatio: false,
                cutout: '65%',
                plugins: {
                    legend: { position: 'bottom', labels: { color: '#94a3b8', padding: 16, usePointStyle: true } }
                }
            }
        });
    },

    renderCashHistory(data) {
        const body = document.getElementById('cash-history-body');
        if (!body) return;
        const fmt = d => d ? new Date(d).toLocaleString('es-CO') : '--';
        body.innerHTML = data.map(c => `
            <tr>
                <td>${c.sessionId?.substring(0, 8) || '--'}</td>
                <td>${c.host || '--'}</td>
                <td>${fmt(c.openedAt)}</td>
                <td>${fmt(c.closedAt)}</td>
                <td><span class="tag ${c.status === 'OPEN' ? 'tag-green' : 'tag-blue'}">${c.status === 'OPEN' ? 'Abierta' : 'Cerrada'}</span></td>
            </tr>
        `).join('');
    }
};
