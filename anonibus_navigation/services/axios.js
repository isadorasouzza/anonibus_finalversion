import axios from 'axios';

const api = axios.create({
  baseURL: 'https://us-central1-anonibus-7d049.cloudfunctions.net',
});

export default api;