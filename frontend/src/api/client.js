import axios from "../../node_modules/axios/index";

const DOMAIN = "http://localhost:8000";

const client = axios.create();

client.defaults.baseURL = DOMAIN;

// client.defaults.headers.common['Authorization'] = 'Bearer ';
// axios.intercepter.response.use

export default client;