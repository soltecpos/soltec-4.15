import React, { useContext } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { AuthContext } from '../context/AuthContext';
import { View, ActivityIndicator } from 'react-native';

import LoginScreen from '../screens/LoginScreen';
import DashboardScreen from '../screens/DashboardScreen';
import TablesScreen from '../screens/TablesScreen';
import TicketDetailScreen from '../screens/TicketDetailScreen';
import CatalogScreen from '../screens/CatalogScreen';

const Stack = createNativeStackNavigator();

const AppNavigator = () => {
    const { isLoading, userToken } = useContext(AuthContext);

    if (isLoading) {
        return (
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <ActivityIndicator size="large" />
            </View>
        );
    }

    return (
        <NavigationContainer>
            <Stack.Navigator>
                {userToken === null ? (
                    <Stack.Screen
                        name="Login"
                        component={LoginScreen}
                        options={{ headerShown: false }}
                    />
                ) : (
                    <>
                        <Stack.Screen
                            name="Dashboard"
                            component={DashboardScreen}
                            options={{ title: 'Soltec POS - Móvil' }}
                        />
                        <Stack.Screen
                            name="Tables"
                            component={TablesScreen}
                            options={{ title: 'Selección de Mesas' }}
                        />
                        <Stack.Screen
                            name="TicketDetail"
                            component={TicketDetailScreen}
                            options={{ title: 'Cuenta de la Mesa' }}
                        />
                        <Stack.Screen
                            name="Catalog"
                            component={CatalogScreen}
                            options={{ title: 'Catálogo de Productos' }}
                        />
                    </>
                )}
            </Stack.Navigator>
        </NavigationContainer>
    );
};

export default AppNavigator;
