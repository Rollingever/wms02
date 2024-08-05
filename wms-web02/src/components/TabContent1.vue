<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入名字" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="add">新增</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="print">打印</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="exportData">导出</el-button>
    </div>
    <el-table ref="table" :data="tableData"
              :header-cell-style="{background:'#f2f5fc',color:'#555'}"
              border>
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="no" label="账号" width="180"></el-table-column>
      <el-table-column prop="name" label="姓名" width="180"></el-table-column>
      <el-table-column prop="age" label="年龄" width="80"></el-table-column>
      <el-table-column prop="sex" label="性别" width="80">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.sex === 1 ? 'primary' : 'success'"
              disable-transitions>{{ scope.row.sex === 1 ? ' 男' : '女' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{
              scope.row.roleId === 0 ? '超级管理员' :
                  (scope.row.roleId === 1 ? '管理员' : '用户')
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？"
              @confirm ="del(scope.row.id)"
              style="margin-left: 5px"
          >
            <el-button slot="reference" size="small" type="danger" >删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="账号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="名字" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model.number="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "TabContent1",
  data() {
    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('年龄输入过大'));
      }else{
        callback();
      }
    };
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(`${this.$httpUrl}/user/findByNo`, { params: { no: this.form.no } })
          .then(res => {
            if (res.data.code === 200 && res.data.data.length > 0) {
              callback(new Error('账号已经存在'));
            } else {
              callback();
            }
          })
          .catch(err => {
            console.error('请求失败', err);
            callback(new Error('验证失败'));
          });
    };
    return {
      tableData: [],
      pageSize: 5,
      pageNum: 1,
      total: 0,
      name: '',
      sex: '',
      sexs: [
        {
          value: '1',
          label: '男'
        }, {
          value: '0',
          label: '女'
        },
      ],
      centerDialogVisible: false,
      form: {
        id:'',
        no: '',
        name: '',
        password: '',
        age: '',
        phone: '',
        sex: '0',
        roleId: '2',
      },
      rules: {
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名字', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'blur'},
          {pattern: /^([1-9][0-9]{0,2})$/, message: '年龄必须为正整数，长度在 1 到 3 位', trigger: 'blur'},
          {validator: checkAge, trigger: 'blur'}
        ],
        phone: [
          {required: true,message: "手机号不能为空",trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger:
                "blur"}
        ]
      },
    }
  },
  methods: {
    exportData() {
      this.$axios.get(this.$httpUrl + '/user/export', { responseType: 'blob' })
          .then(response => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'data.xlsx'); // 文件名可以根据需要更改
            document.body.appendChild(link);
            link.click();
          })
          .catch(err => {
            console.error('导出数据失败', err);
            this.$message.error('导出数据失败');
          });
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id){
      console.log(id)
      this.$axios.get(this.$httpUrl + '/user/del?id='+id).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '操作成功!',
            type: 'success'
          });
          this.loadPost()
        } else {
          this.$message({
            message: '操作失败!',
            type: 'error'
          });
        }
      }).catch(err => {
        console.error('请求失败', err);
      });
    },
    mod(row){

      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.form.id = row.id;
        this.form.no = row.no;
        this.form.name = row.name;
        this.form.password = row.password;
        this.form.age = row.age+'';
        this.form.sex = row.sex+'';
        this.form.phone = row.phone;
        this.form.roleId = row.roleId;
      });
    },
    add(){
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        this.resetForm();
      });
    },
    doMod(){
      this.$axios.post(this.$httpUrl + '/user/update', this.form).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '操作成功!',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
        } else {
          this.$message({
            message: '操作失败!',
            type: 'error'
          });
        }
      }).catch(err => {
        console.error('请求失败', err);
      });
    },
    doSave(){
      this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.$message({
            message: '操作成功!',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
        } else {
          this.$message({
            message: '操作失败!',
            type: 'error'
          });
        }
      }).catch(err => {
        console.error('请求失败', err);
      });
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id){
            this.doMod();
          }else {
            this.doSave();
          }

        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    loadPost() {
      const queryParams = {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          sex: this.sex,
          // roleId: '2'
        }
      };

      // 打印查询参数日志
      console.log("查询参数: ", JSON.stringify(queryParams, null, 2));

      this.$axios.post(this.$httpUrl + '/user/listPage1', queryParams)
          .then(res => res.data)
          .then(res => {
            console.log("响应结果: ", res);
            if (res.code == 200) {
              this.tableData = res.data.users;
              this.total = res.data.total;
            } else {
              alert('获取数据失败');
            }
          })
          .catch(err => {
            console.error('请求失败', err);
          });
    },
    resetParam(){
      this.name = '';
      this.sex = '';
    },
    print() {
      // 检查 this.$refs.table 是否定义
      if (!this.$refs.table) {
        console.error('Table reference not found.');
        return;
      }

      // 获取表格的HTML
      const tableEl = this.$refs.table.$el;

      // 复制表格HTML以避免影响原表格
      const tableCopy = tableEl.cloneNode(true);

      // 移除操作列
      const operationColumnIndex = 7; // 假设操作列是第8列（索引从0开始）
      const rows = tableCopy.querySelectorAll('tr');

      rows.forEach(row => {
        const cells = row.querySelectorAll('th, td');
        if (cells.length > operationColumnIndex) {
          cells[operationColumnIndex].remove();
        }
      });

      // 创建新窗口
      const printWindow = window.open('', '', 'height=600,width=800');

      // 写入HTML
      printWindow.document.write('<html><head><title>打印表格</title>');
      // 添加样式以保持打印格式
      printWindow.document.write('<style>');
      printWindow.document.write(`
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 8px;
    }
    th {
      background-color: #f2f5fc;
      color: #555;
    }
  `);
      printWindow.document.write('</style>');
      printWindow.document.write('</head><body>');
      printWindow.document.write(tableCopy.outerHTML);
      printWindow.document.write('</body></html>');

      // 打印
      printWindow.document.close();
      printWindow.print();
    }
  },

  beforeMount() {
    this.loadPost();
  }
}
</script>

<style scoped>
</style>
