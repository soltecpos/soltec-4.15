import React, { createContext, useState, useEffect } from 'react';
import * as SecureStore from 'expo-secure-store';
import api from '../api/axios';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoading, setIsLoading] = useState(true);
    const [userToken, setUserToken] = useState(null);
    const [userInfo, setUserInfo] = useState(null);

    const login = async (username, password) => {
        try {
            const response = await api.post('/auth/login', { username, password });
            const { token, roleId } = response.data;

            setUserToken(token);
            setUserInfo({ username, roleId });

            await SecureStore.setItemAsync('userToken', token);
            await SecureStore.setItemAsync('userInfo', JSON.stringify({ username, roleId }));

            return true;
        } catch (e) {
            console.error('Login error', e);
            return false;
        }
    };

    const logout = async () => {
        setUserToken(null);
        setUserInfo(null);
        await SecureStore.deleteItemAsync('userToken');
        await SecureStore.deleteItemAsync('userInfo');
    };

    const isLoggedIn = async () => {
        try {
            setIsLoading(true);
            let token = await SecureStore.getItemAsync('userToken');
            let info = await SecureStore.getItemAsync('userInfo');

            if (token) {
                setUserToken(token);
                setUserInfo(JSON.parse(info));
            }
        } catch (e) {
            console.log(`isLogged in error ${e}`);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        isLoggedIn();
    }, []);

    return (
        <AuthContext.Provider value={{ login, logout, isLoading, userToken, userInfo }}>
            {children}
        </AuthContext.Provider>
    );
};
