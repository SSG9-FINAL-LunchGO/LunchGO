import axios from "axios";
import { useAccountStore } from "@/stores/account";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "";
axios.defaults.baseURL = API_BASE_URL;

const instance = axios.create({
    withCredentials: true, // refresh token cookie attach
    baseURL: API_BASE_URL,
});

// response interceptor
instance.interceptors.response.use((res) => {
    return res;
}, async (err) => {
    const accountStore = useAccountStore();

    if (!err.response) {
        return Promise.reject(err);
    }

    if (err.response.status === 401) {
        const originalRequest = err.config || {};
        const isAuthBypass =
            originalRequest.skipAuth ||
            originalRequest.url?.includes('/api/login') ||
            originalRequest.url?.includes('/api/refresh');
        if (isAuthBypass) {
            return Promise.reject(refreshError);
        }

        if (originalRequest._retry) {
            return Promise.reject(err);
        }
        originalRequest._retry = true;

        try {
            const refreshRes = await axios.post('/api/refresh', null, { withCredentials: true });
            const newToken = refreshRes.data?.accessToken ?? refreshRes.data;
            if (!newToken || typeof newToken !== 'string') {
                throw new Error('invalid refresh token response');
            }

            accountStore.setAccessToken(newToken);
            localStorage.setItem('accessToken', newToken);

            originalRequest.headers = originalRequest.headers || {};
            originalRequest.headers.Authorization = `Bearer ${newToken}`;

            return instance(originalRequest);
        } catch (refreshError) {
            const status = refreshError?.response?.status;
            if (status === 401 || status === 403) {
                accountStore.clearAccount?.();
                localStorage.removeItem('accessToken');
                localStorage.removeItem('member');
                window.location.replace('/login');
            }
<<<<<<< Updated upstream

            const res = await axios.post('/api/refresh', {}, {withCredentials: true});

            const {accessToken} = res.data || {};
            if (!accessToken) throw new Error('토큰 리프레시 에러');

            accountStore.setAccessToken(accessToken);
            localStorage.setItem('accessToken', accessToken);

            config.headers = config.headers || {};
            config.headers.Authorization = `Bearer ${accessToken}`;
            config.retried = true;

            return instance(config);
        }
        default:
=======
>>>>>>> Stashed changes
            return Promise.reject(err);
        }
    }

    return Promise.reject(err);
});

const generateConfig = (options = {}) => {
    const { skipAuth, ...rest } = options;

    if (skipAuth) {
        return rest.headers ? { ...rest, headers: { ...rest.headers } } : rest;
    }

    const accountStore = useAccountStore();
    const token = accountStore.accessToken || localStorage.getItem('accessToken');
    const headers = rest.headers ? { ...rest.headers } : {};

    if (token) {
        headers.Authorization = `Bearer ${token}`;
    }

    return Object.keys(headers).length ? { ...rest, headers } : rest;
}

export default {
    get(url, params) {
        const config = generateConfig();
        config.params = params;
        return instance.get(url, config);
    },
    post(url, params, options = {}) {
        return instance.post(url, params, generateConfig(options));
    },
    put(url, params, options = {}) {
        return instance.put(url, params, generateConfig(options));
    },
    patch(url, params, options = {}) {
        return instance.patch(url, params, generateConfig(options));
    },
    delete(url, options = {}) {
        return instance.delete(url, generateConfig(options));
    },
};
