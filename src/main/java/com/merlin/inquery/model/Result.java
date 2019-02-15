package com.merlin.inquery.model;

import java.io.Serializable;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-14 18:18
 */
public class Result implements Serializable {

    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //{"message":"操作成功！","data":{"content":{"detail":0,"zs":2,"ws":0,"bs":0,"bd":2,"notLogin":true}},"code":200}
    //



    /***
     *
     *
     *
     * "queryResultViolation":'{{if results.detail==0}}<div class="bluedi" style="padding-top: 15px;height:
     * 40px; line-height: 20px;"><i class="ico-info-L _text dpIB icon-large"></i> {{if results.zs==0}}
     * 该机动车没有非现场违法未处理记录。 {{else}} 该机动车非现场未处理违法记录共计${results.zs}条。
     * 其中，牌证发放地违法记录${results.bd}条，{{if results.bs>0}}本省异地违法记录${results.bs}条，
     * {{/if}}跨省违法${results.ws}条。{{if results.notLogin}}查看本人机动车违法详情，
     * 请<a href="/m/pubvioquery/route">登录。{{else}}非本人机动车，不能查看违法详情。{{/if}}</a>
     * {{/if}}</div>{{else}}{{if results.length==0}}<div class="bluedi">
     *     <i class="ico-info-L _text dpIB icon-large"></i>您的机动车没有未处理违法记录</div>{{else}}
     *     <table class="table table-striped table-condensed"> <thead> <tr> <td width="110px">违法时间</td>
     *     <td width="200px">违法地点</td> <td>违法行为</td> <td width="70px">是否处理</td> <td width="70px">
     *         是否交款</td> </tr> </thead> <tbody> {{each results}} <tr> <td>${$value.wfsj}</td>
     *         <td title="${$value.wfdz}">${$value.wfdz}</td> <td title="${$value.wfms}">${$value.wfms}</td>
     *         {{if $value.clbj=="1"}} <td class="green">已处理</td> {{else}} <td class="red">未处理</td>
     *         {{/if}} {{if $value.jkbj=="1"}} <td class="green">已交款</td> {{else}} {{if $value.jkbj=="9"}}
     *         <td class="green">无需交款</td> {{else}} <td class="red">未交款</td> {{/if}} {{/if}} </tr>
     *         {{/each}} </tbody> {{/if}}</table>{{/if}}',
     *
     *
     *
     *
     */
}
