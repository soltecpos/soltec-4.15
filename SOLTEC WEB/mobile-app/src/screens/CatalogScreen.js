import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, ActivityIndicator, Image } from 'react-native';
import api from '../api/axios';

const CatalogScreen = ({ route, navigation }) => {
    const { tableId } = route.params;
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState([]);
    const [activeCategory, setActiveCategory] = useState(null);
    const [loading, setLoading] = useState(true);
    const [loadingProducts, setLoadingProducts] = useState(false);

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const response = await api.get('/catalog/categories');
            setCategories(response.data);
            if (response.data.length > 0) {
                setActiveCategory(response.data[0].id);
                fetchProducts(response.data[0].id);
            }
        } catch (e) {
            console.error('Error fetching categories', e);
        } finally {
            setLoading(false);
        }
    };

    const fetchProducts = async (categoryId) => {
        setLoadingProducts(true);
        setActiveCategory(categoryId);
        try {
            const response = await api.get(`/catalog/products?categoryId=${categoryId}`);
            setProducts(response.data);
        } catch (e) {
            console.error('Error fetching products', e);
        } finally {
            setLoadingProducts(false);
        }
    };

    const handleAddProduct = async (product) => {
        try {
            await api.post(`/tickets/${tableId}/addline`, {
                productId: product.id,
                quantity: 1
            });
            // Optionally show a quick toast or let the user keep adding
        } catch (e) {
            alert("Error agregando producto: " + e.message);
        }
    };

    const renderCategory = ({ item }) => (
        <TouchableOpacity
            style={[styles.categoryBtn, activeCategory === item.id && styles.categoryBtnActive]}
            onPress={() => fetchProducts(item.id)}
        >
            <Text style={[styles.categoryText, activeCategory === item.id && styles.categoryTextActive]}>
                {item.name}
            </Text>
        </TouchableOpacity>
    );

    const renderProduct = ({ item }) => (
        <TouchableOpacity style={styles.productCard} onPress={() => handleAddProduct(item)}>
            {item.image ? (
                <Image source={{ uri: `data:image/jpeg;base64,${item.image}` }} style={styles.productImg} />
            ) : (
                <View style={styles.placeholderImg}>
                    <Text style={styles.placeholderText}>IMG</Text>
                </View>
            )}
            <Text style={styles.productName} numberOfLines={2}>{item.name}</Text>
            <Text style={styles.productPrice}>${item.pricesell.toFixed(2)}</Text>

            <View style={styles.addBtn}>
                <Text style={styles.addBtnText}>+</Text>
            </View>
        </TouchableOpacity>
    );

    if (loading) {
        return <ActivityIndicator size="large" style={styles.loader} />;
    }

    return (
        <View style={styles.container}>
            <View style={styles.topBar}>
                <FlatList
                    horizontal
                    showsHorizontalScrollIndicator={false}
                    data={categories}
                    renderItem={renderCategory}
                    keyExtractor={(item) => item.id}
                    contentContainerStyle={styles.catList}
                />
            </View>

            {loadingProducts ? (
                <ActivityIndicator size="large" style={styles.loader} />
            ) : (
                <FlatList
                    data={products}
                    renderItem={renderProduct}
                    keyExtractor={(item) => item.id}
                    numColumns={2}
                    contentContainerStyle={styles.productList}
                />
            )}

            <TouchableOpacity
                style={styles.floatingBtn}
                onPress={() => navigation.navigate('TicketDetail', { tableId })}
            >
                <Text style={styles.floatingBtnText}>VOLVER A LA MESA</Text>
            </TouchableOpacity>
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
    topBar: {
        backgroundColor: '#fff',
        borderBottomWidth: 1,
        borderBottomColor: '#dcdde1',
        paddingVertical: 10,
    },
    catList: {
        paddingHorizontal: 10,
    },
    categoryBtn: {
        paddingHorizontal: 15,
        paddingVertical: 8,
        borderRadius: 20,
        backgroundColor: '#f1f2f6',
        marginRight: 10,
    },
    categoryBtnActive: {
        backgroundColor: '#0984e3',
    },
    categoryText: {
        color: '#2d3436',
        fontWeight: 'bold',
    },
    categoryTextActive: {
        color: '#fff',
    },
    productList: {
        padding: 10,
        paddingBottom: 80, // Space for floating button
    },
    productCard: {
        flex: 1,
        backgroundColor: '#fff',
        margin: 5,
        borderRadius: 12,
        padding: 10,
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.1,
        shadowRadius: 2,
        elevation: 2,
    },
    productImg: {
        width: 80,
        height: 80,
        borderRadius: 8,
        marginBottom: 10,
    },
    placeholderImg: {
        width: 80,
        height: 80,
        borderRadius: 8,
        backgroundColor: '#dfe6e9',
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: 10,
    },
    placeholderText: {
        color: '#b2bec3',
        fontWeight: 'bold',
    },
    productName: {
        fontSize: 14,
        color: '#2d3436',
        textAlign: 'center',
        marginBottom: 5,
        height: 40,
    },
    productPrice: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#00b894',
    },
    addBtn: {
        position: 'absolute',
        right: 5,
        bottom: 5,
        backgroundColor: '#e1b12c',
        width: 30,
        height: 30,
        borderRadius: 15,
        justifyContent: 'center',
        alignItems: 'center',
    },
    addBtnText: {
        color: '#fff',
        fontSize: 20,
        fontWeight: 'bold',
    },
    floatingBtn: {
        position: 'absolute',
        bottom: 20,
        left: 20,
        right: 20,
        backgroundColor: '#d63031',
        padding: 15,
        borderRadius: 10,
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 5,
        elevation: 5,
    },
    floatingBtnText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    }
});

export default CatalogScreen;
