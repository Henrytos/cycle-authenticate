FROM node:20-slim AS builder

WORKDIR /home/app

COPY package.json .

COPY package-lock.json .

RUN npm i

COPY . .

EXPOSE 4200

ENTRYPOINT [ "npm", "start" ]
