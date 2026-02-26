// ============================================
// SOLTEC WEB — Main Application
// ============================================

const App = {
    currentModule: 'dashboard',

    init() {
        this.setupNavigation();
        this.setupClock();
        this.setupMobileMenu();
        this.setupSidebarToggle();
        this.navigate('dashboard');
        this.checkConnection();
    },

    setupNavigation() {
        document.querySelectorAll('.nav-item').forEach(item => {
            item.addEventListener('click', () => {
                const module = item.dataset.module;
                if (module) this.navigate(module);
            });
        });
    },

    setupClock() {
        const clockEl = document.getElementById('clock');
        const update = () => {
            const now = new Date();
            clockEl.textContent = now.toLocaleTimeString('es-CO', {
                hour: '2-digit', minute: '2-digit', second: '2-digit'
            }) + ' · ' + now.toLocaleDateString('es-CO', {
                weekday: 'short', day: 'numeric', month: 'short'
            });
        };
        update();
        setInterval(update, 1000);
    },

    setupMobileMenu() {
        const toggle = document.getElementById('menu-toggle');
        const sidebar = document.getElementById('sidebar');
        toggle.addEventListener('click', () => sidebar.classList.toggle('open'));

        document.getElementById('content-area').addEventListener('click', () => {
            sidebar.classList.remove('open');
        });
    },

    setupSidebarToggle() {
        const logoIcon = document.querySelector('.logo-icon');
        const sidebar = document.getElementById('sidebar');
        if (logoIcon && sidebar) {
            logoIcon.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
            });
        }
    },

    async navigate(module) {
        // Stop kitchen auto-refresh when leaving
        if (this.currentModule === 'kitchen') {
            KitchenModule.stopAutoRefresh();
        }

        this.currentModule = module;

        // Update active nav
        document.querySelectorAll('.nav-item').forEach(item => {
            item.classList.toggle('active', item.dataset.module === module);
        });

        // Update title
        const titles = {
            dashboard: 'Dashboard',
            reports: 'Reportes',
            inventory: 'Inventario',
            users: 'Usuarios',
            tickets: 'Tickets',
            orders: 'Pedidos / Mesas',
            kitchen: 'Pantalla de Cocina'
        };
        document.getElementById('page-title').textContent = titles[module] || module;
        document.getElementById('breadcrumb').textContent = 'Inicio → ' + (titles[module] || module);

        // Show loading
        const content = document.getElementById('content-area');
        content.innerHTML = '<div class="loading-state"><div class="spinner"></div><p>Cargando...</p></div>';

        // Route to module
        try {
            switch (module) {
                case 'dashboard': await this.renderDashboard(); break;
                case 'reports': await ReportsModule.render(); break;
                case 'inventory': await InventoryModule.render(); break;
                case 'users': await UsersModule.render(); break;
                case 'tickets': await TicketsModule.render(); break;
                case 'orders': await OrdersModule.render(); break;
                case 'kitchen': await KitchenModule.render(); break;
                default: content.innerHTML = '<div class="empty-state"><h3>Módulo no encontrado</h3></div>';
            }
        } catch (e) {
            content.innerHTML = `
                <div class="empty-state">
                    <h3>Error al cargar el módulo</h3>
                    <p style="color:var(--accent-red)">${e.message}</p>
                    <button class="btn btn-primary" onclick="App.navigate('${module}')" style="margin-top:16px">Reintentar</button>
                </div>
            `;
        }
    },

    async renderDashboard() {
        const content = document.getElementById('content-area');

        let salesData = { totalSales: 0, totalTickets: 0, paymentBreakdown: {} };
        let cashData = { status: 'UNKNOWN' };
        let openTickets = [];
        let tables = [];

        try {
            [salesData, cashData, openTickets, tables] = await Promise.all([
                API.salesToday().catch(() => salesData),
                API.currentCash().catch(() => cashData),
                API.getOpenTickets().catch(() => []),
                API.getTables().catch(() => [])
            ]);
        } catch (e) { console.error(e); }

        const fmt = (n) => '$' + (n || 0).toLocaleString('es-CO', { minimumFractionDigits: 0 });
        const occupiedTables = tables.filter(t => t.occupied).length;

        content.innerHTML = `
            <div class="animate-in">
                <div class="stats-grid">
                    <div class="stat-card blue" onclick="App.navigate('reports')" style="cursor:pointer">
                        <div class="stat-icon blue">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Ventas Hoy</div>
                            <div class="stat-value">${fmt(salesData.totalSales)}</div>
                        </div>
                    </div>
                    <div class="stat-card orange" onclick="App.navigate('tickets')" style="cursor:pointer">
                        <div class="stat-icon orange">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M15 5v2M15 11v2M15 17v2M5 5h14a2 2 0 0 1 2 2v3a2 2 0 0 0 0 4v3a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-3a2 2 0 0 0 0-4V7a2 2 0 0 1 2-2z"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Tickets Hoy</div>
                            <div class="stat-value">${salesData.totalTickets}</div>
                        </div>
                    </div>
                    <div class="stat-card green" onclick="App.navigate('orders')" style="cursor:pointer">
                        <div class="stat-icon green">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="18" height="18" rx="2"/><line x1="3" y1="9" x2="21" y2="9"/><line x1="9" y1="21" x2="9" y2="9"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Mesas Ocupadas</div>
                            <div class="stat-value">${occupiedTables} / ${tables.length}</div>
                        </div>
                    </div>
                    <div class="stat-card purple" onclick="App.navigate('kitchen')" style="cursor:pointer">
                        <div class="stat-icon purple">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2L2 7l10 5 10-5-10-5z"/><path d="M2 17l10 5 10-5"/><path d="M2 12l10 5 10-5"/></svg>
                        </div>
                        <div class="stat-info">
                            <div class="stat-label">Pedidos Abiertos</div>
                            <div class="stat-value">${openTickets.length}</div>
                        </div>
                    </div>
                </div>

                <div class="grid-2">
                    <div class="card">
                        <div class="card-header">
                            <span class="card-title">Estado de Caja</span>
                            <span class="tag ${cashData.status === 'OPEN' ? 'tag-green' : 'tag-red'}">${cashData.status === 'OPEN' ? 'Abierta' : 'Cerrada'}</span>
                        </div>
                        <div style="color:var(--text-secondary);font-size:0.88rem">
                            ${cashData.host ? '<div>Host: <strong>' + cashData.host + '</strong></div>' : ''}
                            ${cashData.openedAt ? '<div>Apertura: ' + new Date(cashData.openedAt).toLocaleString('es-CO') + '</div>' : ''}
                            ${cashData.totalSales !== undefined ? '<div style="margin-top:8px;font-size:1.2rem;font-weight:700;color:var(--accent-green)">Total en caja: ' + fmt(cashData.totalSales) + '</div>' : ''}
                            ${cashData.ticketCount !== undefined ? '<div>Tickets procesados: ' + cashData.ticketCount + '</div>' : ''}
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <span class="card-title">Accesos Rápidos</span>
                        </div>
                        <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px">
                            <button class="btn btn-secondary" onclick="App.navigate('reports')" style="justify-content:center">📊 Reportes</button>
                            <button class="btn btn-secondary" onclick="App.navigate('inventory')" style="justify-content:center">📦 Inventario</button>
                            <button class="btn btn-secondary" onclick="App.navigate('users')" style="justify-content:center">👥 Usuarios</button>
                            <button class="btn btn-secondary" onclick="App.navigate('kitchen')" style="justify-content:center">🍳 Cocina</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    },

    async checkConnection() {
        const dot = document.querySelector('.status-dot');
        const label = dot?.nextElementSibling;
        try {
            await API.health();
            dot?.classList.add('online');
            dot?.classList.remove('offline');
            if (label) label.textContent = 'Conectado';
        } catch (e) {
            dot?.classList.remove('online');
            dot?.classList.add('offline');
            if (label) label.textContent = 'Desconectado';
        }
        setTimeout(() => this.checkConnection(), 15000);
    }
};

// Start app
document.addEventListener('DOMContentLoaded', () => App.init());
