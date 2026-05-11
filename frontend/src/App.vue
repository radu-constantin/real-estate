<script setup>
import axios from "axios";

const properties = await axios.get('/api/properties')

console.log(properties)
</script>

<template>
  <h1>Hello World!</h1>
  <li>
    <ul v-for="property in properties">
      {{property}}
    </ul>
  </li>
</template>

<style scoped></style>
