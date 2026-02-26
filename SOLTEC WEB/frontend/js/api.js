// ============================================
// SOLTEC WEB — API Client
// ============================================

const API = {
    // Use relative path '/api' so it works on any IP address (e.g. 192.168.x.x) when served by Spring Boot.
    // If testing from port 3000 (React/Vanilla dev server), it will fallback to localhost:8085.
    BASE_URL: window.location.port === '3000' ? 'http://localhost:8085/api' : '/api',

    async request(endpoint, options = {}) {
        const url = `${this.BASE_URL}${endpoint}`;
        const config = {
            headers: { 'Content-Type': 'application/json' },
            ...options
        };
        try {
            const response = await fetch(url, config);
            if (!response.ok) throw new Error(`HTTP ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error(`API Error [${endpoint}]:`, error);
            throw error;
        }
    },

    get(endpoint) { return this.request(endpoint); },

    post(endpoint, data) {
        return this.request(endpoint, { method: 'POST', body: JSON.stringify(data) });
    },

    put(endpoint, data) {
        return this.request(endpoint, { method: 'PUT', body: JSON.stringify(data) });
    },

    delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    },

    // Health
    health() { return this.get('/health'); },

    // Reports
    salesToday() { return this.get('/reports/sales/today'); },
    salesRange(from, to) { return this.get(`/reports/sales/range?from=${from}&to=${to}`); },
    topProducts(from, to, limit = 10) {
        let url = `/reports/products/top?limit=${limit}`;
        if (from) url += `&from=${from}`;
        if (to) url += `&to=${to}`;
        return this.get(url);
    },
    currentCash() { return this.get('/reports/cash/current'); },
    cashHistory(limit = 20) { return this.get(`/reports/cash/history?limit=${limit}`); },

    // Inventory
    getProducts(search, category) {
        let url = '/inventory/products';
        const params = [];
        if (search) params.push(`search=${encodeURIComponent(search)}`);
        if (category) params.push(`category=${category}`);
        if (params.length) url += '?' + params.join('&');
        return this.get(url);
    },
    getProduct(id) { return this.get(`/inventory/products/${id}`); },
    updateProduct(id, data) { return this.put(`/inventory/products/${id}`, data); },
    createProduct(data) { return this.post('/inventory/products', data); },
    deleteProduct(id) { return this.delete(`/inventory/products/${id}`); },
    getCategories() { return this.get('/inventory/categories'); },
    getStock() { return this.get('/inventory/stock'); },

    // Users
    getUsers() { return this.get('/users'); },
    getUser(id) { return this.get(`/users/${id}`); },
    updateUser(id, data) { return this.put(`/users/${id}`, data); },
    createUser(data) { return this.post('/users', data); },
    getRoles() { return this.get('/users/roles'); },

    // Tickets
    getOpenTickets() { return this.get('/tickets/open'); },
    getClosedTickets(date) {
        let url = '/tickets/closed';
        if (date) url += `?date=${date}`;
        return this.get(url);
    },
    getTicket(id) { return this.get(`/tickets/${id}`); },

    // Orders
    getTables() { return this.get('/orders/tables'); },
    getFloors() { return this.get('/orders/floors'); },

    // Kitchen
    getPendingOrders() { return this.get('/kitchen/pending'); }
};
