export function Api(authToken) {

  const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  };

  const BASE_URL = '/api/books';

  function createHeaders() {
    return authToken ? {
      ...headers,
      'Authorization': 'Bearer ' + authToken
    } : headers;
  }

  async function getAll() {
    return await fetch(BASE_URL, {
      method: 'GET',
      headers: createHeaders()
    });
  }

  async function getById(id) {
    console.log(id)
    return await fetch(`${BASE_URL}/${id}`, {
      method: 'GET',
      headers: createHeaders()
    });
  }

  async function deleteItem(id) {
    return await fetch(`${BASE_URL}/${id}`, {
      method: 'DELETE',
      headers: createHeaders()
    });
  }

  async function update(item) {
    return await fetch(`${BASE_URL}/${item.id}`, {
      method: 'PUT',
      headers: createHeaders(),
      body: JSON.stringify(item)
    });
  }

  async function create(item) {
    return await fetch(BASE_URL, {
      method: 'POST',
      headers: createHeaders(),
      body: JSON.stringify(item)
    });
  }
}

export default Api;

/*
class Api {

  constructor(authToken) {
    this.authToken = authToken;
  }

  headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  };

  BASE_URL = '/api/books';

  createHeaders() {
    return this.authToken ? {
      ...this.headers,
      'Authorization': 'Bearer ' + this.authToken
    } : this.headers;
  }

  async getAll() {
    return await fetch(this.BASE_URL, {
      method: 'GET',
      headers: this.createHeaders()
    });
  }

  async getById(id) {
    console.log(id)
    return await fetch(`${this.BASE_URL}/${id}`, {
      method: 'GET',
      headers: this.createHeaders()
    });
  }

  async delete(id) {
    return await fetch(`${this.BASE_URL}/${id}`, {
      method: 'DELETE',
      headers: this.createHeaders()
    });
  }

  async update(item) {
    return await fetch(`${this.BASE_URL}/${item.id}`, {
      method: 'PUT',
      headers: this.createHeaders(),
      body: JSON.stringify(item)
    });
  }

  async create(item) {
    return await fetch(this.BASE_URL, {
      method: 'POST',
      headers: this.createHeaders(),
      body: JSON.stringify(item)
    });
  }
}

export default Api;
*/