import axios from 'axios';

const server = 'http://localhost:7002';

const API = {
  get: path => axios.get(`${server}${path}`).then(response => response.data),
  post: (path,body) => axios.post(`${server}${path}`,body).then(response => response.data),
};

export default API;
