<template>
  <v-app id="inspire">
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-12">
              <v-toolbar color="rgb(134, 138, 140)" dark flat>
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-card-text>
                <v-form ref="form" v-model="valid" lazy-validation @submit.prevent="onSubmit">
                  <p
                    v-if="error"
                    style="font-size:15px; color: red;"
                    align="center"
                  >아이디 또는 패스워드가 틀렸습니다.</p>
                  <v-text-field
                    v-model="id"
                    :rules="idRules"
                    label="id"
                    name="id"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    v-model="password"
                    :rules="passwordRules"
                    id="password"
                    label="Password"
                    name="password"
                    prepend-icon="mdi-lock"
                    type="password"
                  ></v-text-field>
                  <v-card-actions>
                    <v-checkbox v-model="idSave" color="rgb(134, 138, 140)" label="아이디 저장"></v-checkbox>
                    <!-- <v-checkbox v-model="checkbox" :label="`Checkbox 1: ${checkbox.toString()}`"></v-checkbox> -->
                    <v-spacer></v-spacer>
                    <!-- <v-btn color="rgb(134, 138, 140)" dark type="">비밀번호 찾기</v-btn> -->
                    <v-btn color="rgb(134, 138, 140)" dark type="submit">Login</v-btn>
                  </v-card-actions>
                </v-form>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { mapState } from "vuex";

export default {
  // props: {
  //   source: String
  // },
  data: () => ({
    valid: true,
    id: "",
    idRules: [v => !!v || "id is required"],
    password: "",
    passwordRules: [v => !!v || "password is required"],
    returnPath: "",
    error: "",
    idSave: false
  }),
  created() {
    this.returnPath = this.$route.query.returnPath || "/";
    // cookie에서 idSave가 유효하면
    if (this.$cookie.get("idSave")) {
      // 로그인폼 불러올 때, 아이디 체크 true, id값 true
      this.idSave = true;
      this.id = this.$cookie.get("id");
    }
  },
  computed: {
    ...mapState({
      accessToken: "accessToken",
      userInfo: "userInfo"
    })
  },
  methods: {
    onSubmit() {
      const { id, password } = this;
      this.$store
        .dispatch("LOGIN", { id, password })
        .then(() => {
          console.log("submit ok--------------------------------------------")
          if (this.idSave == true) {
            this.$cookie.set("id", id, 1);
            this.$cookie.set("idSave", this.idSave, 1);
          } else {
            this.$cookie.delete("id");
            this.$cookie.delete("idSave");
          }

          if(this.userInfo.userType=="s") {
            this.$router.push('/user_management')
          }else{
            this.$router.push(this.returnPath);
          }
        })
        .catch(() => {
          console.log("submit error--------------------------------------------")
          this.error = true;
        });
    }
  }
};
</script>

<style>
</style>