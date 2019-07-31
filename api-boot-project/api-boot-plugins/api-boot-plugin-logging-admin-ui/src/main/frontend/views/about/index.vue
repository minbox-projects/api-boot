<!--
  - Copyright 2014-2018 the original author or authors.
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -->

<template>
  <div class="section">
    <div class="container">
      <h1 class="title" v-text="$t('about.title')" />
      <h2 class="subtitle" v-if="version" v-text="`当前版本 ${version}`" />
      <div class="content">
        <p>
          ApiBoot Logging Admin 是分布式链路日志组件ApiBoot Logging的管理端界面应用程序。
        </p>
        <p>
          可以查看各个ApiBoot Logging业务组件的上报情况，分析出每条链路的耗时、调用频率等信息。<br/>
          1. 可查看每条链路日志详情（请求参数、头信息、响应内容、状态码等，详见文档.）<br/>
          2. 可查看当前上报日志的业务服务组件列表（可查看最后一次上报日志的时间）<br/>
          3. 可分析出实时请求总数、耗时最大的请求地址等<br/>
        </p>
        <p>
          在使用过程中，如果你遇到了Bug、或者有建议要提交，请访问
          <a href="https://gitee.com/hengboy/api-boot/issues" target="_blank">
            提出你的问题
          </a>.
        </p>
        <div class="about-links">
          <a class="button is-primary is-outlined"
             :href="`${documentationBaseUrl}`" target="_blank"
          >
            <font-awesome-icon size="lg" icon="book" />&nbsp;使用文档
          </a>
          <a class="button is-black is-outlined" href="https://github.com/hengboy/api-boot" target="_blank">
            <font-awesome-icon size="lg" :icon="['fab', 'github']" />&nbsp;GitHub源码
          </a>
          <a class="button is-gitter is-outlined" href="https://gitee.com/hengboy/api-boot" target="_blank">
            <font-awesome-icon size="lg" :icon="['fab', 'gitter']" />&nbsp;码云源码
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data: () => ({
      // eslint-disable-next-line no-undef
      version: __PROJECT_VERSION__
    }),
    computed: {
      documentationBaseUrl() {
        return `http://apiboot.minbox.io/zh-cn/docs/api-boot-logging-admin.html`;
      }
    },
    install({viewRegistry}) {
      viewRegistry.addView({
        path: '/about',
        name: 'about',
        label: 'about.label',
        order: 200,
        component: this
      });
    }
  };
</script>

<style lang="scss">
  .about-links {
    display: flex;
    align-items: center;

    & > * {
      margin-right: 0.5rem;
    }
  }

  $stackoverflow: #f48024;
  .button.is-stackoverflow.is-outlined {
    background-color: transparent;
    border-color: $stackoverflow;
    color: $stackoverflow;

    &:hover,
    &:focus {
      background-color: $stackoverflow;
      border-color: $stackoverflow;
      color: white;
    }
  }

  $gitter: #ed1965;
  .button.is-gitter.is-outlined {
    background-color: transparent;
    border-color: $gitter;
    color: $gitter;

    &:hover,
    &:focus {
      background-color: $gitter;
      border-color: $gitter;
      color: white;
    }
  }
</style>
