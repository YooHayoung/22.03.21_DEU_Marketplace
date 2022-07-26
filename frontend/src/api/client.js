import axios from "../../node_modules/axios/index";

export const AWS_SERVER = "http://52.79.237.93:8000";
export const AWS_CLIENT = "http://52.79.237.93:3000";
export const LOCAL_SERVER = "http://localhost:8000";
export const LOCAL_CLIENT = "http://localhost:3000";

// export const DOMAIN = AWS_SERVER;
// export const THIS_CLIENT = AWS_CLIENT;
export const DOMAIN = LOCAL_SERVER;
export const THIS_CLIENT = LOCAL_CLIENT;

const client = axios.create();

client.defaults.baseURL = DOMAIN;

// client.defaults.headers.common['Authorization'] = 'Bearer ';
// axios.intercepter.response.use

export default client;