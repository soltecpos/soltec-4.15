// ============================================
// Module: Inventory
// ============================================

const InventoryModule = {

    async render() {
        const content = document.getElementById('content-area');
        content.innerHTML = `
            <div class="animate-in">
                <div class="toolbar">
                    <div class="search-box">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>
                        <input type="text" id="inv-search" placeholder="Buscar productos..." oninput="InventoryModule.search(this.value)">
                    </div>
                    <select class="form-select" id="inv-category" style="width:180px" onchange="InventoryModule.filterCategory(this.value)">
                        <option value="">Todas las categorías</option>
                    </select>
                    <button class="btn btn-primary" onclick="InventoryModule.showCreateModal()">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
                        Nuevo Producto
                    </button>
                </div>

                <div class="card">
                    <div style="overflow-x:auto">
                        <table class="data-table">
                            <thead><tr>
                                <th>Nombre</th><th>Referencia</th><th>Código</th><th>P. Compra</th><th>P. Venta</th><th>Tipo</th><th>Acciones</th>
                            </tr></thead>
                            <tbody id="products-body">
                                <tr><td colspan="7"><div class="loading-state"><div class="spinner"></div></div></td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        `;

        await this.loadCategories();
        await this.loadProducts();
    },

    async loadCategories() {
        try {
            const categories = await API.getCategories();
            const select = document.getElementById('inv-category');
            categories.forEach(c => {
                const opt = document.createElement('option');
                opt.value = c.id;
                opt.textContent = c.name;
                select.appendChild(opt);
            });
        } catch (e) { console.error(e); }
    },

    async loadProducts(search, category) {
        try {
            const products = await API.getProducts(search, category);
            this.renderProducts(products);
        } catch (e) {
            document.getElementById('products-body').innerHTML =
                '<tr><td colspan="7" class="empty-state">Error cargando productos</td></tr>';
        }
    },

    renderProducts(products) {
        const body = document.getElementById('products-body');
        if (!products.length) {
            body.innerHTML = '<tr><td colspan="7"><div class="empty-state"><h3>No hay productos</h3></div></td></tr>';
            return;
        }
        const fmt = n => '$' + (n || 0).toLocaleString('es-CO', { minimumFractionDigits: 0 });
        body.innerHTML = products.map(p => `
            <tr>
                <td><strong>${this.esc(p.name)}</strong></td>
                <td>${this.esc(p.reference)}</td>
                <td>${this.esc(p.code)}</td>
                <td>${fmt(p.pricebuy || 0)}</td>
                <td style="color:var(--accent-green);font-weight:600">${fmt(p.pricesell)}</td>
                <td><span class="tag ${p.isservice ? 'tag-blue' : 'tag-green'}">${p.isservice ? 'Servicio' : 'Producto'}</span></td>
                <td>
                    <button class="btn btn-secondary btn-sm" onclick="InventoryModule.showEditModal('${p.id}')">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="InventoryModule.deleteProduct('${p.id}')">Eliminar</button>
                </td>
            </tr>
        `).join('');
    },

    search(val) {
        clearTimeout(this._searchTimer);
        this._searchTimer = setTimeout(() => this.loadProducts(val), 300);
    },

    filterCategory(catId) {
        this.loadProducts(null, catId || undefined);
    },

    showCreateModal() {
        this.showProductModal(null);
    },

    async showEditModal(id) {
        try {
            const product = await API.getProduct(id);
            this.showProductModal(product);
        } catch (e) { alert('Error cargando producto'); }
    },

    showProductModal(product) {
        const isEdit = !!product;
        const overlay = document.createElement('div');
        overlay.className = 'modal-overlay';
        overlay.id = 'product-modal';
        overlay.innerHTML = `
            <div class="modal">
                <div class="modal-header">
                    <h2>${isEdit ? 'Editar' : 'Nuevo'} Producto</h2>
                    <button class="modal-close" onclick="document.getElementById('product-modal').remove()">✕</button>
                </div>
                <div class="form-group">
                    <label class="form-label">Nombre</label>
                    <input class="form-input" id="prod-name" value="${isEdit ? this.esc(product.name) : ''}">
                </div>
                <div style="display:grid;grid-template-columns:1fr 1fr;gap:12px">
                    <div class="form-group">
                        <label class="form-label">Precio Compra</label>
                        <input class="form-input" type="number" id="prod-pricebuy" value="${isEdit ? product.pricebuy || 0 : 0}">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Precio Venta</label>
                        <input class="form-input" type="number" id="prod-pricesell" value="${isEdit ? product.pricesell || 0 : 0}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" onclick="document.getElementById('product-modal').remove()">Cancelar</button>
                    <button class="btn btn-primary" id="save-product-btn">Guardar</button>
                </div>
            </div>
        `;
        document.body.appendChild(overlay);

        document.getElementById('save-product-btn').onclick = async () => {
            const data = {
                name: document.getElementById('prod-name').value,
                pricebuy: parseFloat(document.getElementById('prod-pricebuy').value),
                pricesell: parseFloat(document.getElementById('prod-pricesell').value)
            };
            try {
                if (isEdit) await API.updateProduct(product.id, data);
                else await API.createProduct(data);
                overlay.remove();
                this.loadProducts();
            } catch (e) { alert('Error guardando producto'); }
        };
    },

    async deleteProduct(id) {
        if (!confirm('¿Eliminar este producto?')) return;
        try {
            await API.deleteProduct(id);
            this.loadProducts();
        } catch (e) { alert('Error eliminando producto'); }
    },

    esc(str) { return str ? String(str).replace(/</g, '&lt;').replace(/>/g, '&gt;') : ''; }
};
