import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, ActivityIndicator } from 'react-native';
import { useFocusEffect } from '@react-navigation/native';
import api from '../api/axios';

const TicketDetailScreen = ({ route, navigation }) => {
    const { tableId, tableName } = route.params;
    const [ticketData, setTicketData] = useState(null);
    const [loading, setLoading] = useState(true);

    const fetchTicket = async () => {
        try {
            const response = await api.get(`/tickets/${tableId}`);
            setTicketData(response.data);
        } catch (e) {
            if (e.response && e.response.status === 404) {
                setTicketData({ lines: [] }); // Empty new ticket
            } else {
                console.error('Error fetching ticket', e);
            }
        } finally {
            setLoading(false);
        }
    };

    useFocusEffect(
        React.useCallback(() => {
            fetchTicket();
        }, [tableId])
    );

    const sendToKitchen = async () => {
        try {
            setLoading(true);
            await api.post(`/tickets/${tableId}/sendToKitchen`);
            alert("Comandas enviadas a cocina.");
            fetchTicket();
        } catch (e) {
            alert("Error enviando a cocina: " + e.message);
            setLoading(false);
        }
    };

    const renderProductLine = ({ item }) => (
        <View style={styles.lineItem}>
            <Text style={styles.lineUnits}>{item.units}x</Text>
            <View style={styles.lineDesc}>
                <Text style={styles.lineName}>{item.productName}</Text>
                <Text style={styles.lineStatus}>
                    {item.sendstatus === 'Yes' ? '✓ Enviado' : '⏳ Pendiente'}
                </Text>
            </View>
            <Text style={styles.linePrice}>${(item.total || 0).toFixed(2)}</Text>
        </View>
    );

    const calculateTotal = () => {
        if (!ticketData || !ticketData.lines) return 0;
        return ticketData.lines.reduce((acc, curr) => acc + (curr.total || 0), 0);
    };

    if (loading) {
        return <ActivityIndicator size="large" style={styles.loader} />;
    }

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.headerText}>Mesa: {tableName}</Text>
                <Text style={styles.totalText}>Total: ${calculateTotal().toFixed(2)}</Text>
            </View>

            <FlatList
                data={ticketData?.lines || []}
                renderItem={renderProductLine}
                keyExtractor={(item, index) => index.toString()}
                ListEmptyComponent={<Text style={styles.emptyText}>Mesa sin pedidos.</Text>}
                contentContainerStyle={styles.list}
            />

            <View style={styles.footer}>
                <TouchableOpacity style={styles.catalogBtn} onPress={() => navigation.navigate('Catalog', { tableId })}>
                    <Text style={styles.btnText}>+ CATÁLOGO</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.kitchenBtn} onPress={sendToKitchen} disabled={!ticketData?.lines?.length}>
                    <Text style={styles.btnText}>ENVIAR A COCINA</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
    loader: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    header: {
        backgroundColor: '#2d3436',
        padding: 20,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    headerText: {
        color: '#fff',
        fontSize: 20,
        fontWeight: 'bold',
    },
    totalText: {
        color: '#00b894',
        fontSize: 22,
        fontWeight: 'bold',
    },
    list: {
        padding: 10,
    },
    lineItem: {
        flexDirection: 'row',
        padding: 15,
        borderBottomWidth: 1,
        borderBottomColor: '#dfe6e9',
        alignItems: 'center',
    },
    lineUnits: {
        fontSize: 18,
        fontWeight: 'bold',
        marginRight: 15,
        width: 30,
    },
    lineDesc: {
        flex: 1,
    },
    lineName: {
        fontSize: 16,
        color: '#2d3436',
    },
    lineStatus: {
        fontSize: 12,
        color: '#b2bec3',
        marginTop: 2,
    },
    linePrice: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#d63031',
    },
    emptyText: {
        textAlign: 'center',
        marginTop: 50,
        fontSize: 16,
        color: '#636e72',
    },
    footer: {
        flexDirection: 'row',
        padding: 10,
        borderTopWidth: 1,
        borderColor: '#dfe6e9',
        backgroundColor: '#f5f6fa',
    },
    catalogBtn: {
        flex: 1,
        backgroundColor: '#0984e3',
        padding: 15,
        borderRadius: 8,
        marginRight: 5,
        alignItems: 'center',
    },
    kitchenBtn: {
        flex: 1,
        backgroundColor: '#e1b12c',
        padding: 15,
        borderRadius: 8,
        marginLeft: 5,
        alignItems: 'center',
    },
    btnText: {
        color: '#fff',
        fontWeight: 'bold',
        fontSize: 14,
    }
});

export default TicketDetailScreen;
