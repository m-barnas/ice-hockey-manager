import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080/pa165/rest'
});

// instance.defaults.headers.common['Authorization'] = 'AUTH TOKEN FROM INSTANCE';

// instance.interceptors.request...

export default instance;