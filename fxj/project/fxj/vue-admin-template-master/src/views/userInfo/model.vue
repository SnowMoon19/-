<template>
  <div>
    <el-card class="model">
      <div class="tittle">{{ model.type }}</div>
      <div v-if="model.creator.toString()===id" class="creator">
        提出者：{{ name }}
      </div>
      <div v-else class="creator">
        提出者：{{ anotherName }}
      </div>
      <div class="creator">
        始于：{{ model.startTime }}
      </div>
      <div class="over_model">
        结束请求：
        <div class="over_raiser">
          <div v-if="model.over_1===1">
            {{ name }}
          </div>
          <div v-else-if="model.over_2===1">
            {{ anotherName }}
          </div>
        </div>
        <el-button type="primary" >raise</el-button>
        <!--todo 显示-->
        <el-dialog :visible.sync="infoDialogVisible" :modal="false" :title="infoPhoto[0].date" width="900px" append-to-body>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getAnotherId, getAnotherUsername, getId, getName } from '../../utils/auth'
import { getModel } from '../../api/userInfo/model'

export default {
  data() {
    return {
      id: getId(),
      anotherId: getAnotherId(),
      name: getName(),
      anotherName: getAnotherUsername(),
      model: {}
    }
  },
  created() {
    this.getModel()
  },
  methods: {
    getModel() {
      getModel(this.id).then(response => {
        this.model = response.data
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
/**
  设置滚动条样式
 */
::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 40px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 8px;
}
::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(97, 184, 179, 0.1);
  background: white;
}
::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  box-shadow: inset 0 0 5px rgba(87, 175, 187, 0.1);
  border-radius: 10px;
  //background: #a0e6e3;
}

.model {
  margin: 10% 10% 10% 10%;
  height: 500px;
  background: #52aeff;
  border-radius: 25px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, 0.8)
}
.tittle {
  margin: 10px 10% 10px 10%;
  text-align: center;
  font-size: 40px;
  height: 50px;
  background: #fff0f5
}
.creator {
  margin: 10px 10% 10px 10%;
  font-size: 30px;
  height: 50px;
  background: #fff0f5
}
/**
  结束当前模式
 */
.over_model {
  margin: 10px 10% 10px 10%;
  font-size: 30px;
  height: 50px;
  background: #fff0f5;
  //.over_tittle {
  //  display: inline-block; // 行内块
  //  width: 20%;
  //  background: blue;
  //}
  /**
    提出者的名字
   */
  .over_raiser {
    display: inline-block;
    background: red;
  }
}
</style>
