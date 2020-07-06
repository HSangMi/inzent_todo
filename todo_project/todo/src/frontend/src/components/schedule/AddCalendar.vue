<template>
  <v-dialog v-model="dialog" max-width="700px">
    <v-stepper v-model="e1">
      <v-divider></v-divider>
      <v-stepper-header>
        <v-stepper-step :complete="e1 > 1" step="1">SELECT PROJECT</v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="e1 > 2" step="2">ADD TASK</v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <v-card class="mb-12" height="200px">
            <v-card-title class="text-h5">1. SELCT PROJECT</v-card-title>
            <v-select :items="selectProjects" label="PROJECT" outlined></v-select>
            <v-select :items="selectTasks" label="TASKS" outlined></v-select>
          </v-card>
          <!-- <v-icon absolute right large @click="e1 = 2">mdi-chevron-right</v-icon> -->
          <v-btn text @click="dialog=false">CANCEL</v-btn>
          <v-btn absolute right class="ma-2" color="primary" fab x-small dark @click="e1 = 2">
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-card class="mb-12" height="200px">
            <v-card-title class="text-h5">2. ADD TASK</v-card-title>
          </v-card>

          <!-- <v-btn class="ma-2" tile outlined color="primary" @click="e1 = 1">PREV</v-btn> -->
          <v-btn color="primary">ADD</v-btn>
          <v-btn text @click="dialog=false">CANCEL</v-btn>
          <v-btn absolute right class="ma-2" color="primary" fab x-small dark @click="e1 = 1">
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-dialog>
</template>

<script>
export default {};
</script>

<style>
</style>