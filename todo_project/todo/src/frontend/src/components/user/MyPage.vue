<template>
  <v-dialog v-model="mypageModal" width="600" persistent >
    <v-card>
      <!-- <v-form
        ref="form"
        v-model="valid"
        @submit.prevent="onSubmit"
        lazy-validation
      > -->
      <v-card-title class="headline grey lighten-2"  primary-title>
        <span class="headline">프로필 수정</span>
      </v-card-title>
      <v-container style="padding: 50px 50px 10px 50px;">
        <v-row>
          <v-col class="py-0">
            <v-hover>
            <template v-slot:default="{ hover }">
              <div class="img-profile">
                <v-avatar size="164px">
                  <template v-if="userInfo.imgCode">
                    <img :src="userInfo.imgCode">
                  </template>
                  <template v-else>
                    <v-img src="../../assets/기본사용자.png" />
                  </template>
                  <v-overlay 
                    absolute
                    v-if="hover"
                  >
                  <v-file-input
                    v-model="userInfo.imgCode"
                    :rules="rules.img"
                    accept="image/*"
                    prepend-icon="mdi-camera"
                    hide-input
                    @change="createBase64Image(userInfo.imgCode)"
                  ></v-file-input>
                  </v-overlay>
                </v-avatar>
              </div>
            </template>
            </v-hover>
          </v-col>
        </v-row>
        <v-row>
          <v-col
            class="py-0"
            cols="12"
            sm="12"
          >
            <v-text-field
              v-model="userInfo.name"
              label="이름"
              clearable
              @focusout="updateUser('name', userInfo.name)"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
            class="py-0"
            cols="12"
            sm="12"
          >
            <v-text-field
              v-model="password"
              label="비밀번호"
              readonly
              @click.prevent="dialog = true"
            ></v-text-field>
          </v-col>
        </v-row>
        <!-- 비밀번호 변경 dialog -->
        <v-dialog v-model="dialog" max-width="290">
          <v-card v-if="next">
            <v-card-title>
              <span class="headline">비밀번호 변경</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="12" class="py-0">
                     <v-text-field
                        v-model="newPassword"
                        :rules="[rules.required, rules.min]"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show1 ? 'text' : 'password'"
                        @click:append="show1 = !show1"
                        label="새로운 비밀번호"
                      />
                       <v-text-field
                        v-model="rePassword"
                        :rules="[rules.required, rules.min, passwordMatch]"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show2 ? 'text' : 'password'"
                        @click:append="show2 = !show2"
                        label="새로운 비밀번호 확인"
                      />
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="green darken-1" text @click="cancel">CANCEL</v-btn>
              <v-btn color="green darken-1" text @click="updateUser('password', rePassword)">UPDATE</v-btn>
            </v-card-actions>
          </v-card>
          <v-card v-else>
            <v-card-title>
              <span class="headline">비밀번호 변경</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="12">
                    <v-text-field 
                      label="현재 비밀번호" 
                      v-model="prePassword" 
                      type="password"  
                    ></v-text-field>
                    <p
                    v-if="error"
                    style="font-size:15px; color: red;"
                    align="center"
                    >패스워드가 틀렸습니다.</p>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="green darken-1" text @click="dialog = false">CANCEL</v-btn>
              <v-btn color="green darken-1" text @click.prevent="pwdCheck">NEXT</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-row>
          <v-col
            class="py-0"
            cols="12"
            sm="6"
          >
            <v-menu
              ref="menu"
              v-model="menu"
              :close-on-content-click="false"
              :return-value.sync="date"
              transition="scale-transition"
              offset-y
              min-width="290px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="userInfo.birth"
                  label="생년월일"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                />
              </template>
              <v-date-picker
                v-model="userInfo.birth"
                no-title
                scrollable
              >
                <v-spacer />
                <v-btn
                  text
                  color="primary"
                  @click="menu = false"
                >
                  Cancel
                </v-btn>
                <v-btn
                  text
                  color="primary"
                  @click="dateEvent"
                >
                  OK
                </v-btn>
              </v-date-picker>
            </v-menu>
          </v-col>
          <v-col
            class="py-0"
            cols="12"
            sm="6"
          >
            <v-select
              v-model="userInfo.gender"
              :items="genders"
              label="성별"
              return-object
              @change="updateUser('gender', userInfo.gender)"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
            class="py-0"
            cols="12"
            sm="12"
          >
            <v-text-field
              v-model="userInfo.phone"
              label="휴대전화"
              clearable
              @focusout="updateUser('phone', userInfo.phone)"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
            class="py-0"
            cols="12"
            sm="12"
          >
            <v-text-field
              v-model="userInfo.email"
              label="이메일"
              clearable
              @focusout="updateUser('email', userInfo.email)"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
            class="py-0"
            cols="12"
            sm="6"
          >
            <v-text-field
              v-model="userInfo.deptName"
              label="부서"
              disabled
            />
          </v-col>
          <v-col
            class="py-0"
            cols="12"
            sm="6"
          >
            <v-select
              v-model="userInfo.rank"
              :items="ranks"
              label="직급"
              @change="updateUser('rank', userInfo.rank)"
            />
          </v-col>
        </v-row>
        <!-- <br><br>
        <v-row>   
          <v-input
            hint
            persistent-hint
            label="구글 계정 연동"
          />
          <v-switch
            v-model="switch1"
            inset
          />
        </v-row> -->
      </v-container>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="onClose">Cancle</v-btn>
        <!-- <v-btn color="blue darken-1" text type="submit">Create</v-btn> -->
        <v-btn color="blue darken-1" text @click="onClose">Update</v-btn>
      </v-card-actions>
      <!-- </v-form> -->
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  props: ["mypageModal"],
  data:() => ({
    show1: false,
    show2: false,
    rules: {
      required: value => !!value || "Required.",
      min: v => v.length >= 8 || "Min 8 characters",
      // passwordMatch: () => "The password you entered don't match"
      img: v => !v || v.size < 2000000 || 'File size should be less than 2MB!'
    },
    dialog: false,
    date: "",
    menu: false,
    overlay: false,
    imgCode: "",
    password: "********",
    newPassword: "",
    rePassword: "",
    prePassword: "",
    genders: ["여성", "남성","비공개"],
    ranks: ["부장", "과장", "차장", "대리", "사원"],
    switch1: true,
    next: false,
    error: "",
  }),
  computed: {
    ...mapState({
      userInfo: "userInfo"
    }),
    passwordMatch() {
      return () => (this.newPassword === this.rePassword) || "The password you entered don't match"
    }
  },
  methods: {
    ...mapActions(["UPDATE_USER"]),
    
    pwdCheck() {
      const id = this.userInfo.id;
      const password = this.prePassword;
      this.$store.dispatch("PWD_CHECK", { id, password })
      .then((res) => {
        if(res) {
          this.next = res;        
        } else {
          this.error = true;
        }
      })
    },

    cancel() {
      this.dialog = false;
      this.next = false;
    },
    
    updateUser(key, value) {
      const id = this.userInfo.id
      console.log("id: " + id)
      console.log("key: " + key)
      console.log("value: " + value)
      this.UPDATE_USER({id, key, value})      
      if(key == "password") {
        this.cancel()
        this.prePassword = ""
        this.newPassword = ""
        this.rePassword = ""
      }
    },

    dateEvent() {
      this.$refs.menu.save(this.userInfo.birth)
      this.updateUser('birth', this.userInfo.birth)
    },
    
    createBase64Image(fileObject) {
      const reader = new FileReader();
      reader.onload = (e) => {        
        this.image = e.target.result;
        console.log("image", this.image)
        this.updateUser('imgCode', this.image)
      };
      reader.readAsDataURL(fileObject);
    },

    // onSubmit() {
    //   console.log("update User");
    //   // if(this.vaildate()) {
        
    //   // }
    // },

    onClose() {
      this.$emit("close");
    },
  }
}
</script>

<style>
.img-profile{
  width: 150px;
  margin-right: auto !important;
  margin-left: auto !important;
}
</style>