
ARG NODE_IMAGE=node:20-alpine
# Name the node stage "builder"
FROM ${NODE_IMAGE} as node
# Set working directory
WORKDIR /app
# Copy all files from current directory to working dir in image
COPY . .
# install node modules
RUN npm install
RUN npm ci
# build assets
RUN npm run build --prod
# nginx state for serving content
FROM nginx:alpine
# Set working directory to nginx asset directory
WORKDIR /usr/share/nginx/html
# Remove default nginx static assets
RUN rm -rf ./*
# Copy config
COPY ./nginx.conf /etc/nginx/nginx.conf
# Copy static assets from builder stage
COPY --from=node /app/dist/weather-forecast-app/browser .

EXPOSE 80
# Containers run nginx with global directives and daemon off
CMD ["nginx", "-g", "daemon off;"]

