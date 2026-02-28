import axios from 'axios';
import * as SecureStore from 'expo-secure-store';

// Configured automatically with the machine's Tailscale IP
export const API_BASE_URL = 'http://100.101.109.68:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
});

api.interceptors.request.use(
    async (config) => {
        const token = await SecureStore.getItemAsync('userToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default api;
