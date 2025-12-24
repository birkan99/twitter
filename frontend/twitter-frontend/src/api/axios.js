import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:3000/workintech/twitter/project/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    // Token varsa Header'a ekle
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;