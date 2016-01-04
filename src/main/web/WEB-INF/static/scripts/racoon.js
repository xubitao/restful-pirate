/**
 * Created by xubitao on 1/2/16.
 */

var host = "http://localhost:8080";
var Racoon = {
    restful: function (_options) {
        $.ajax({
            type: _options.type || "GET",
            url: _options.url,
            dataType: _options.dataType || "json",
            async: _options.async || true,
            success: function (_data) {
                toastr.clear();
                toastr.success('成功!');
                _options.success(_data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var httpErrorModel = $("#httpErrorModel");
                if (httpErrorModel.html() == undefined) {
                    loadErrorModel(XMLHttpRequest.responseJSON.message)
                }
                $('#errorModel').modal();
            }
        });
    },
    getLink: function (_links, _rel) {
        for (var index in _links) {
            var link = _links[index];
            if (link.rel == _rel) {
                return link.href;
            }
        }
    }
};

function loadErrorModel(_message) {
    Racoon.restful({
        url: host + "/static/html/errorModel.html",
        dataType: "html",
        async: false,
        success: function (_data) {
            var errorModel = $(_data);
            errorModel.find("#errorMessage").html(_message);
            $("body").append(errorModel);
            $('#errorModel').modal();
        }
    })
}