function Parse(pubhcolumn, pushdata) {
           vJSON_COLUMN_NAME_SPLIT = ","
            vJSON_DATA_COLUMN_SPLIT = "`"
            vJSON_DATA_ROW_SPLIT = "§"
       //   var argv = sportsSys.Common.compressToJSON.arguments;
        //   var vPlusIndexColumn = (argv.length > 2) ? argv[2] : false;
         var  vPlusIndexColumn=true;
          if (vPlusIndexColumn == true) {
                pubhcolumn += vJSON_COLUMN_NAME_SPLIT + "RowIndexId"
            }
            var vHeaderArr = pubhcolumn.split(vJSON_COLUMN_NAME_SPLIT);
            var vBodyArr = pushdata.split(vJSON_DATA_ROW_SPLIT);
            var vResult = "";
            var vbodyLen = vBodyArr.length;
            var vheaderLen = vHeaderArr.length;
            var i = 0;
            while (i < vbodyLen) {
                if (vPlusIndexColumn == true) {
                    vBodyArr[i] += vJSON_DATA_COLUMN_SPLIT + i.toString()
                }
                vBodyRowArr = vBodyArr[i].split(vJSON_DATA_COLUMN_SPLIT);
                vResult += "{";
                var j = 0;
                while (j < vheaderLen) {
                    vResult += '"' + vHeaderArr[j] + '":"' + vBodyRowArr[j] + '"' + ((j == vheaderLen - 1) ? "" : ",");
                    j++
                }
                vResult += "}" + ((i == vbodyLen - 1) ? "" : ",");
                i++
            }
            return (pushdata == "") ? "[]" : "[" + vResult + "]"
        }