# Etapa 1: build del proyecto Angular
FROM node:20 AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

# Limpiar posibles alteraciones
RUN rm -rf dist/

# Forzar build cliente (SPA) sin usar SSR
RUN npx ng build --configuration docker


# Etapa 2: servidor web usando Nginx
FROM nginx:alpine

# Copiar SOLO el contenido real del build Angular (SPA)
COPY --from=build /app/dist/frontend/browser/ /usr/share/nginx/html/

# Configuración de Nginx para rutas Angular
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
