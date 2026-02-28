import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, ActivityIndicator } from 'react-native';
import api from '../api/axios';

const TablesScreen = ({ navigation }) => {
    const [tables, setTables] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchTables = async () => {
        try {
            const response = await api.get('/orders/tables');
            setTables(response.data);
        } catch (e) {
            console.error('Error fetching tables', e);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTables();
    }, []);

    const renderTable = ({ item }) => {
        const isOccupied = item.occupied;
        return (
            <TouchableOpacity
                style={[styles.tableItem, isOccupied ? styles.tableOccupied : styles.tableFree]}
                onPress={() => navigation.navigate('TicketDetail', { tableId: item.id, tableName: item.name })}
            >
                <Text style={[styles.tableName, isOccupied && { color: '#fff' }]}>{item.name}</Text>
                <Text style={[styles.tableStatus, isOccupied && { color: '#f1f2f6' }]}>
                    {isOccupied ? 'Ocupada' : 'Libre'}
                </Text>
            </TouchableOpacity>
        );
    };

    if (loading) {
        return <ActivityIndicator size="large" style={styles.loader} />;
    }

    return (
        <View style={styles.container}>
            <FlatList
                data={tables}
                renderItem={renderTable}
                keyExtractor={(item) => item.id.toString()}
                numColumns={2}
                contentContainerStyle={styles.listContainer}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f5f6fa',
    },
    loader: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    listContainer: {
        padding: 10,
    },
    tableItem: {
        flex: 1,
        margin: 10,
        height: 100,
        borderRadius: 12,
        justifyContent: 'center',
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },
    tableFree: {
        backgroundColor: '#fff',
        borderColor: '#2ecc71',
        borderWidth: 2,
    },
    tableOccupied: {
        backgroundColor: '#e74c3c',
    },
    tableName: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#2d3436',
    },
    tableStatus: {
        fontSize: 14,
        color: '#636e72',
        marginTop: 5,
    }
});

export default TablesScreen;
