import axios from 'axios';
import { logout, setIsAuthenticated, loginSuccess } from '../redux/features/auth';
import store from '../redux/store/store';
import toast from 'react-hot-toast';
import { setCategories } from '../redux/features/category';

// For development, use API Gateway directly
export const API_BASE_URL = window.__RUNTIME_CONFIG__.VITE_BACKEND_URL || import.meta.env.VITE_BACKEND_URL || "http://localhost:9000";
console.log("backend url from k8s: ", window.__RUNTIME_CONFIG__.VITE_BACKEND_URL);

export const API_CONFIG = {
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
};

export const api = axios.create(API_CONFIG);

export const INTERNAL_SERVER_ERROR = "Internal server error!"

api.interceptors.response.use(
    response => response,
    async (error) => {

        const dispatch = store.dispatch;
        if (error?.response?.status === 403) { // Token expired
            console.log(">>> intercepter: Token expired or not found")
            dispatch(setIsAuthenticated({ isAuthenticated: false }));
            try {
                const response = await api.post(`auth/refresh_token`, {}, { withCredentials: true }); // Send refresh token via cookie
                if (response.data.success) {
                    console.log(response.data.data.object)
                    const userData = {
                        username: response.data.data.object.username,
                        email: response.data.data.object.email,
                        token: response.data.data.object.accessToken,
                        currency: response.data.data.object.currency,
                        plan: response.data.data.object.plan,
                        role: response.data.data.object.role,
                    }
                    dispatch(loginSuccess(userData))
                    dispatch(setCategories(response.data.data.object.categories))
                    error.config.headers['Authorization'] = `Bearer ${response.data.data.object.accessToken}`;
                    console.log("Retrying resquest")
                    return axios(error.config); // Retry the request
                }
            } catch (error) {
                toast.error(INTERNAL_SERVER_ERROR)
                console.log("Not authenticated. error while getting access token via refresh token: ", error)
                window.location.href = '/login';
            }
        }
        return Promise.reject(error);
    }
);

export const refreshAccessToken = async (dispatch) => {
    try {
        console.log(">>>> Requesting refresh token")
        const response = await api.post(`auth/refresh_token`, {}, { withCredentials: true }); // Send refresh token via cookie
        if (response.data.success) {
            console.log(response.data.data.object)
            const userData = {
                username: response.data.data.object.username,
                email: response.data.data.object.email,
                token: response.data.data.object.accessToken,
                currency: response.data.data.object.currency,
                plan: response.data.data.object.plan,
                role: response.data.data.object.role,
            }
            dispatch(loginSuccess(userData))
            dispatch(setCategories(response.data.data.object.categories))
        }
        console.log(">>> Refresh token pass")
    } catch (error) {
        console.log(">>> Refresh token expired. navigate to login: ", error)
        dispatch(logout());
        window.location.href = '/login';
        toast.info("Your session has expired. Please login.")
    }
};



