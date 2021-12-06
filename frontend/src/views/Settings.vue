<template>
<div class="w-100" >
  <div class="d-flex flex-column align-items-center">
    <h1>FirstName LastName</h1>
    <h3>Email Address</h3>
    <p>Password</p>
    <input v-model="password" placeholder="abcd1234">
    <p>Confirm Password </p>
    <input v-model="confirmPassword" placeholder="abcd1234">
    <button >Save</button>
  </div>
  </div>
</template>

<script>
//import axios from "axios";
export default {
  //components: {
  //},
    data() {
        return {
            password: "",
            confirmPassword: ""
        }
    }
}
</script>