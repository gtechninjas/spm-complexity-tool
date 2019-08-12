export const HOST = 'localhost';
export const PORT = '9090';

// true or false as the parameter
export const getEndpoint = (isHttps) => {
  return `${isHttps ? 'https' : 'http'}://${HOST}:${PORT}`;
};
