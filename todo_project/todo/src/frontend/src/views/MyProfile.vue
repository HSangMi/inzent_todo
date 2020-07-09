<template>
  <v-form>
    
    <!-- <v-container>
      <v-row justify="center">
        <v-btn @click.prevent="isOpenSearch = true">
            open
          </v-btn>
        <user-search :openModal="isOpenSearch" @close="isOpenSearch = false" />
      </v-row>
    </v-container> -->

  <v-row>
  <v-col cols=4>
    <v-card
      class="mx-4 my-5 pa-5"
      max-width="700px"
      height="850px"
    >
      <v-card-title style="font-size:20px;">
        나의 프로필 수정
      </v-card-title>
      <v-container>
        <v-row>
          <v-col>
            <div class="img-profile">
              <v-avatar size="150">
                <img :src="getImgCode()"  class="mx-auto">
              </v-avatar>
            </div>
          </v-col>
        </v-row>
        <v-row>
          <v-col
            cols="12"
            sm="12"
          >
            <v-text-field
              v-model="this.userInfo.name"
              label="이름"
              clearable
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
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
                  <v-col cols="12" sm="12">
                     <v-text-field
                        v-model="newPassword"
                        :rules="[rules.required, rules.min]"
                        type="password"
                        label="새로운 비밀번호"
                      />
                       <v-text-field
                        v-model="rePassword"
                        :rules="[rules.required, rules.min, passwordMatch]"
                        type="password"
                        label="새로운 비밀번호 확인"
                      />
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="green darken-1" text @click.prevent="pwdCheck">확인</v-btn>
              <v-btn color="green darken-1" text @click.prevent="cancel">취소</v-btn>
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
                    <v-text-field label="현재 비밀번호" v-model="prePassword" type="password" required></v-text-field>
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
              <v-btn color="green darken-1" text @click.prevent="pwdCheck">확인</v-btn>
              <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-row>
          <v-col
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
                  v-model="date"
                  label="생년월일"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                />
              </template>
              <v-date-picker
                v-model="date"
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
                  @click="$refs.menu.save(date)"
                >
                  OK
                </v-btn>
              </v-date-picker>
            </v-menu>
          </v-col>
          <v-col
            class="d-flex"
            cols="12"
            sm="6"
          >
            <v-select
              v-model="this.userInfo.gender"
              :items="gender"
              label="성별"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
            cols="12"
            sm="12"
          >
            <v-text-field
              v-model="this.userInfo.phone"
              label="휴대전화"
              clearable
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
            cols="12"
            sm="12"
          >
            <v-text-field
              v-model="this.userInfo.email"
              label="이메일"
              clearable
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col
            class="d-flex"
            cols="12"
            sm="6"
          >
            <v-text-field
              v-model="this.userInfo.deptName"
              label="부서"
              disabled
            />
          </v-col>
          <v-col
            class="d-flex"
            cols="12"
            sm="6"
          >
            <v-select
              v-model="this.userInfo.rank"
              :items=rank
              label="직급"
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
    </v-card>
  </v-col>
  <v-col>
    <v-card
      class="mx-4 my-5 pa-5"
      max-width="1500px"
      height="850px"
    >
      <v-card-title style="font-size:20px;">
        사내 조직도
      </v-card-title>
      <v-container fluid>
        <v-row>
          <v-col cols="3">
            <v-treeview
              v-model="selection"
              :items="items"
              selectable
              selection-type="leaf"
              return-object
              open-all
              open-on-click
              @input="selectDept"
            ></v-treeview>
          </v-col>
          <v-divider vertical></v-divider>
          <v-col>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
            <v-data-table
              :headers="headers"
              :items="this.userList"
              :search="search"
              v-model="selected"
              >
              <template v-slot:item.avatar="{ item }">
                <v-avatar size="32px" v-if="item.imgCode">
                  <!-- <img :src="'data:image;base64,'+item.imgCode"> -->
                  <img :src="getListImgCode(item)">
                </v-avatar>
                <v-avatar size="32px" v-else>
                  <v-icon>mdi-account</v-icon>
                </v-avatar>
              </template> 
            </v-data-table>      
          </v-col>
                    
        </v-row>
        <v-row>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text >확인</v-btn>
          <v-btn color="blue darken-1" text @click="onClose">취소</v-btn>
        </v-row>
        
      </v-container>
    </v-card>
  </v-col>
  </v-row>
  </v-form>
</template>

<script>
import { mapState, mapActions} from "vuex";

export default {
  data:() => ({
    isOpenSearch: false,
    show1: false,
    rules: {
      required: value => !!value || "Required.",
      min: v => v.length >= 8 || "Min 8 characters",
      // passwordMatch: () => "The password you entered don't match"
    },
    dialog: false,
    date: "",
    menu: false,
    gender: ["여성", "남성","비공개"],
    deptName: "",
    rank: ["부장", "과장", "차장", "대리", "사원"],
    switch1: true,
    password: "********",
    newPassword: "",
    rePassword: "",
    prePassword: "",
    next: false,
    error: "",
    selection: [],
    items: [],
    search: '',
    selected: [],
    headers: [
      {
        text: '사진',
        align: 'start',
        sortable: false,
        value: 'avatar',
      },
      { text: '이름', value: 'name' },
      { text: '부서', value: 'deptName' },
      { text: '직급', value: 'rank' },
      { text: '연락처', value: 'phone' },
      { text: '이메일', value: 'email' },
    ],
    model: null, 
    myTree: '',
  }),
  created() {
    // this.fatchData(),
    this.date = this.userInfo.birth
    this.fatch_data()
  },
  computed: {
    ...mapState({
    //   userList: "userList",
      userInfo: "userInfo",
      deptList: "deptList",
      userList: "userList"
    }),
    passwordMatch() {
      return () => (this.newPassword === this.rePassword) || "The password you entered don't match"
    }
  },
  components: {
    // UserSearch: () => import("../components/user/UserSearch.vue"),
  },
  methods: {
    // ...mapActions(["FATCH_USERLIST"]),
    // fatchData() {
    //   this.FATCH_USERLIST();
    // },
    ...mapActions(["FATCH_DEPTLIST", "FATCH_USERLIST"]),

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
    getImgCode() {
      return 'data:image;base64,' + this.userInfo.imgCode
    },

    fatch_data() {
      this.FATCH_DEPTLIST().then(() => {
        let list = this.deptList

        console.log("list", list)
        list.forEach(item => {
          for(let i=0; i<list.length; i++) {
            if(item.parentDeptCode ===list[i].deptCode) {
                item.parentId = list[i].id
                break;
            }
          }
        })
        console.log("foreach list", list)

        let myTree = null
        list.forEach(item => {
          if(myTree === null) {
            myTree={id: item.id, name: item.deptName}
            console.log(';',myTree)
          } else {
            this.Recursive(item, myTree)
          }
        })
        // console.log("myTree", myTree)

        let treeArr = [];
        treeArr.push(myTree);

        this.items = treeArr;
        this.myTree = myTree;
      });
    },

    Recursive(item,tree) {
      //tree Id 와 item parentId가 같다면 
      if(tree.id === item.parentId) {
        if(typeof tree.children === "undefined") { // tree에 children이 없다면
          tree.children = []
            tree.children.push({id : item.id, name : item.deptName})
        }else { // tree에 children이 있다.
          tree.children.push({id : item.id, name : item.deptName})
        }
      }else { //tree Id와 item parentId가 다름
        if(typeof tree.children === "undefined") { // tree에 children이 없다면
          return ;
        }else { // tree에 children이 있다면
           tree.children.forEach(child => {
             this.Recursive(item,child)
           })
        }
      }
    },

    onClose() {
      this.$emit("close")
    },
    remove (item) {
      console.log(item)
      const index = this.selected.indexOf(item);
      console.log("index: " + index);
      if (index >= 0) this.selected.splice(index, 1);
    },

    selectDept(){
      const selectedDept = []
      for(let i=0; i<this.selection.length; i++) {
        var dptmt = this.deptList.find(item =>{
          return item.id == this.selection[i].id
        })
        selectedDept.push(dptmt.deptCode)
      }

      console.log("myTree", this.myTree)
      console.log("chlidren id " + this.myTree.children[0].id)
      console.log(selectedDept)

      this.FATCH_USERLIST(selectedDept)
    },
    getListImgCode(item) {
      return 'data:image;base64,' + item.imgCode
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
.v-treeview-node__root {
  min-height: 35px;
}
</style>


