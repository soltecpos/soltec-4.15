// ============================================
// Module: Users
// ============================================

const UsersModule = {

    async render() {
        const content = document.getElementById('content-area');
        content.innerHTML = `
            <div class="animate-in">
                <div class="toolbar">
                    <div style="flex:1">
                        <h3 style="color:var(--text-secondary);font-weight:500">Gestión de usuarios del POS</h3>
                    </div>
                    <button class="btn btn-primary" onclick="UsersModule.showCreateModal()">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
                        Nuevo Usuario
                    </button>
                </div>
                <div class="grid-3" id="users-grid">
                    <div class="loading-state"><div class="spinner"></div></div>
                </div>
            </div>
        `;
        await this.loadUsers();
    },

    async loadUsers() {
        try {
            const users = await API.getUsers();
            this.renderUsers(users);
        } catch (e) {
            document.getElementById('users-grid').innerHTML = '<div class="empty-state"><h3>Error cargando usuarios</h3></div>';
        }
    },

    renderUsers(users) {
        const grid = document.getElementById('users-grid');
        if (!users.length) {
            grid.innerHTML = '<div class="empty-state"><h3>No hay usuarios</h3></div>';
            return;
        }
        grid.innerHTML = users.map(u => `
            <div class="card" style="cursor:pointer" onclick="UsersModule.showEditModal('${u.id}')">
                <div style="display:flex;align-items:center;gap:14px">
                    <div style="width:48px;height:48px;border-radius:50%;background:var(--gradient-blue);display:flex;align-items:center;justify-content:center;font-weight:700;font-size:1.1rem;flex-shrink:0">
                        ${(u.name || '?')[0].toUpperCase()}
                    </div>
                    <div style="flex:1;min-width:0">
                        <div style="font-weight:600;font-size:1rem;margin-bottom:2px">${this.esc(u.name)}</div>
                        <span class="tag ${u.visible ? 'tag-green' : 'tag-red'}">${u.visible ? 'Activo' : 'Inactivo'}</span>
                    </div>
                </div>
            </div>
        `).join('');
    },

    showCreateModal() { this.showUserModal(null); },

    async showEditModal(id) {
        try {
            const user = await API.getUser(id);
            this.showUserModal(user);
        } catch (e) { alert('Error'); }
    },

    async showUserModal(user) {
        const isEdit = !!user;
        let roles = [];
        try { roles = await API.getRoles(); } catch (e) { }

        const overlay = document.createElement('div');
        overlay.className = 'modal-overlay';
        overlay.id = 'user-modal';
        overlay.innerHTML = `
            <div class="modal">
                <div class="modal-header">
                    <h2>${isEdit ? 'Editar' : 'Nuevo'} Usuario</h2>
                    <button class="modal-close" onclick="document.getElementById('user-modal').remove()">✕</button>
                </div>
                <div class="form-group">
                    <label class="form-label">Nombre</label>
                    <input class="form-input" id="user-name" value="${isEdit ? this.esc(user.name) : ''}">
                </div>
                <div class="form-group">
                    <label class="form-label">Rol</label>
                    <select class="form-select" id="user-role">
                        ${roles.map(r => `<option value="${r.id}" ${isEdit && user.roleId === r.id ? 'selected' : ''}>${r.name}</option>`).join('')}
                    </select>
                </div>
                <div class="form-group">
                    <label class="form-label">
                        <input type="checkbox" id="user-visible" ${!isEdit || user.visible ? 'checked' : ''}> Activo
                    </label>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" onclick="document.getElementById('user-modal').remove()">Cancelar</button>
                    <button class="btn btn-primary" id="save-user-btn">Guardar</button>
                </div>
            </div>
        `;
        document.body.appendChild(overlay);

        document.getElementById('save-user-btn').onclick = async () => {
            const data = {
                name: document.getElementById('user-name').value,
                roleId: document.getElementById('user-role').value,
                visible: document.getElementById('user-visible').checked
            };
            try {
                if (isEdit) await API.updateUser(user.id, data);
                else await API.createUser(data);
                overlay.remove();
                this.loadUsers();
            } catch (e) { alert('Error guardando usuario'); }
        };
    },

    esc(str) { return str ? String(str).replace(/</g, '&lt;').replace(/>/g, '&gt;') : ''; }
};
