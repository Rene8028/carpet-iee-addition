# carpet-iee-addition
[![License](https://img.shields.io/github/license/Rene8028/carpet-iee-addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Rene8028/carpet-iee-addition.svg)](https://github.com/Rene8028/carpet-iee-addition/issues)
[![Parent](https://img.shields.io/badge/Parent-fabric--carpet-blue)](https://github.com/gnembon/fabric-carpet)
[![Version](https://img.shields.io/badge/Version-Beta-blue)](https://github.com/Rene8028/carpet-iee-addition)

## 这是一个 [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) 的扩展 mod，包含了不少~~NotVanilla的~~有意思的特性

### 本mod仍在Beta版本，相关功能未完善，仍有bug谨慎使用！！

### Beta Version DO NOT USE!!

### 本项目已经废弃，后续不再更新维护！！


本mod还没完善，欢迎各位大佬指教

TODO:
1. Debug!!!
2. 添加英文翻译
3. 咕咕咕

## 规则

### DEBUG模式 (IEECA-DEBUG)

开启后会显示一些DEBUG信息至消息栏

- 类型: `boolean`
- 默认值: `false`
- 参考选项: `false`, `true`
- 分类: `TEST`, `FEATURE`, `CREATIVE`

### 深板岩刷石机 (DeepalateGeneration)

如果下面有裂纹深板岩砖，则会生成深板岩圆石而不是圆石

- 类型: `boolean`
- 默认值: `false`
- 参考选项: `false`, `true`
- 分类: `IEE`, `FEATURE`, `SURVIVAL`
### 可再生海洋之心 (renewableHeart_of_the_sea)

守卫者被骷髅杀死有概率掉落海洋之心

设置为-1来禁用
- 类型: `double`
- 默认值: `0.0D`
- 参考选项: `1.0`,`0.2`, `0.0`
- 分类: `IEE`, `FEATURE`, `SURVIVAL`
### 禁用看门狗 (DisableWatchdog)

禁用ServerWatchdog看门狗

- 类型: `boolean`
- 默认值: `false`
- 参考选项: `false`, `true`
- 分类: `IEE`
### 可再生海绵 (renewableSponge)

在地狱停留一定时间的守卫者转变成远古守卫者(自定义时间)

设置为-1来禁用，单位：刻(tick)

- 类型: `int`
- 默认值: `-1`
- 参考选项: `-1`, `30`, `60`,`90`
- 分类: `IEE`, `FEATURE`, `SURVIVAL`
