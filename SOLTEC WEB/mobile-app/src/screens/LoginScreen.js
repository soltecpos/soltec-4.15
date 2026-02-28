import React, { useState, useContext } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, ActivityIndicator } from 'react-native';
import { AuthContext } from '../context/AuthContext';

const LoginScreen = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const { login } = useContext(AuthContext);

    const handleLogin = async () => {
        if (!username) {
            setError('Por favor, ingresa el usuario');
            return;
        }

        setLoading(true);
        setError(null);

        // We expect the Context to handle the auth API call
        const success = await login(username, password);

        if (!success) {
            setError('Credenciales incorrectas o error de red');
        }

        setLoading(false);
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>SOLTEC POS</Text>
            <Text style={styles.subtitle}>Acceso Móvil</Text>

            {error && <Text style={styles.errorText}>{error}</Text>}

            <View style={styles.inputContainer}>
                <TextInput
                    style={styles.input}
                    placeholder="Nombre de Usuario"
                    value={username}
                    onChangeText={setUsername}
                    autoCapitalize="none"
                />

                <TextInput
                    style={styles.input}
                    placeholder="Contraseña / PIN"
                    value={password}
                    onChangeText={setPassword}
                    secureTextEntry
                />

                <TouchableOpacity
                    style={styles.button}
                    onPress={handleLogin}
                    disabled={loading}
                >
                    {loading ? (
                        <ActivityIndicator color="#fff" />
                    ) : (
                        <Text style={styles.buttonText}>INGRESAR</Text>
                    )}
                </TouchableOpacity>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#f5f6fa',
        padding: 20,
    },
    title: {
        fontSize: 32,
        fontWeight: 'bold',
        color: '#2d3436',
        marginBottom: 5,
    },
    subtitle: {
        fontSize: 18,
        color: '#636e72',
        marginBottom: 40,
    },
    inputContainer: {
        width: '100%',
        maxWidth: 400,
        backgroundColor: '#fff',
        padding: 20,
        borderRadius: 10,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },
    input: {
        height: 50,
        borderColor: '#dfe6e9',
        borderWidth: 1,
        borderRadius: 8,
        marginBottom: 15,
        paddingHorizontal: 15,
        fontSize: 16,
        backgroundColor: '#f9f9f9',
    },
    button: {
        backgroundColor: '#0984e3',
        height: 50,
        borderRadius: 8,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 10,
    },
    buttonText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold',
    },
    errorText: {
        color: '#d63031',
        marginBottom: 15,
        textAlign: 'center',
    }
});

export default LoginScreen;
