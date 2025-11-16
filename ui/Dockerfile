FROM node:22-alpine AS build
WORKDIR /app
COPY package.json .
RUN npm install --silent
COPY . .
RUN npm run build:prod

FROM nginx:1.29-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/mensa-rater/browser/ /usr/share/nginx/html
