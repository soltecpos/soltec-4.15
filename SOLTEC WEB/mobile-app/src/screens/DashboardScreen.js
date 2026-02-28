import React, { useContext } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { AuthContext } from '../context/AuthContext';

const DashboardScreen = ({ navigation }) => {
    const { logout, userInfo } = useContext(AuthContext);

    return (
        <View style={styles.container}>
            <Text style={styles.welcomeText}>Bienvenido, {userInfo?.username}</Text>

            <View style={styles.gridContainer}>
                <TouchableOpacity style={styles.card} onPress={() => navigation.navigate('Tables')}>
                    <Text style={styles.cardTitle}>Mesas</Text>
                    <Text style={styles.cardDesc}>Tomar pedidos y enviar a cocina</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.card} onPress={() => { }}>
                    <Text style={styles.cardTitle}>Inventario</Text>
                    <Text style={styles.cardDesc}>Consultar stock de productos</Text>
                </TouchableOpacity>

                {(userInfo?.roleId === '0' || userInfo?.roleId === 'admin') && (
                    <TouchableOpacity style={[styles.card, styles.adminCard]} onPress={() => { }}>
                        <Text style={[styles.cardTitle, { color: '#fff' }]}>Caja</Text>
                        <Text style={[styles.cardDesc, { color: '#f1f2f6' }]}>Arqueos y cierres</Text>
                    </TouchableOpacity>
                )}
            </View>

            <TouchableOpacity style={styles.logoutButton} onPress={logout}>
                <Text style={styles.logoutText}>Cerrar Sesión</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f5f6fa',
        padding: 15,
    },
    welcomeText: {
        fontSize: 22,
        fontWeight: 'bold',
        marginBottom: 20,
        color: '#2f3640',
    },
    gridContainer: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'space-between',
    },
    card: {
        width: '48%',
        backgroundColor: '#fff',
        padding: 20,
        borderRadius: 12,
        marginBottom: 15,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.1,
        shadowRadius: 2,
        elevation: 2,
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: 120,
    },
    adminCard: {
        backgroundColor: '#e1b12c',
    },
    cardTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#34495e',
        marginBottom: 5,
    },
    cardDesc: {
        fontSize: 12,
        color: '#7f8c8d',
        textAlign: 'center',
    },
    logoutButton: {
        marginTop: 'auto',
        backgroundColor: '#e84118',
        padding: 15,
        borderRadius: 8,
        alignItems: 'center',
    },
    logoutText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    }
});

export default DashboardScreen;
