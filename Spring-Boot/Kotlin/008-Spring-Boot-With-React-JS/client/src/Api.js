export function Api(authToken) {

  let headers = {
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

  const getAll = async () => {
    return await fetch(BASE_URL, {
      method: 'GET',
      headers: createHeaders()
    });
  }

  const getById = async (id) => {
    console.log(id)
    return await fetch(`${BASE_URL}/${id}`, {
      method: 'GET',
      headers: createHeaders()
    });
  }

  const deleteItem = async (id) => {
    return await fetch(`${BASE_URL}/${id}`, {
      method: 'DELETE',
      headers: createHeaders()
    });
  }

  const update = async (item) => {
    return await fetch(`${BASE_URL}/${item.id}`, {
      method: 'PUT',
      headers: createHeaders(),
      body: JSON.stringify(item)
    });
  }

  const create = async (item) => {
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