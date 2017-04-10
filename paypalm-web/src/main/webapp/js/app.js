/**
 * @author Serhii Shymkiv
 * @copyright 2014
 */

$(document).ready(function() {
    $.fn.unCheckAllCheckBoxes = function() {
        var checkBoxArray =
            $("[type='checkbox']").toArray();

        for(var i = 0; i < checkBoxArray.length; i++){
            checkBoxArray[i].checked = false;
        }
    };
});