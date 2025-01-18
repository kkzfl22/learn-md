<template>
  <div>
      <Student name="nullnull" sex="女"  :age="12"/>
  </div>
</template>

<script>
  import Student from './components/Student.vue'

  export default {
    name : 'App',
    components: {Student}
  }
</script>

