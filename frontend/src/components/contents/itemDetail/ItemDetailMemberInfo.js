import React from "react";
import { Avatar, Box, Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailMemberInfo = (props) => {

    function stringToColor(string) {
        let hash = 0;
        let i;
      
        /* eslint-disable no-bitwise */
        for (i = 0; i < string.length; i += 1) {
          hash = string.charCodeAt(i) + ((hash << 5) - hash);
        }
      
        let color = '#';
      
        for (i = 0; i < 3; i += 1) {
          const value = (hash >> (i * 8)) & 0xff;
          color += `00${value.toString(16)}`.slice(-2);
        }
        /* eslint-enable no-bitwise */
      
        return color;
      }

    function stringAvatar(name) {
        return {
          sx: {
            bgcolor: stringToColor(name),
          },
          children: `${name.substr(0,1)}`,
        };
    }

    return (
        <Box className="div_memInfo">
            <div className="label">판매자</div>
            <Avatar {...stringAvatar(props.memberInfo.nickname)} id="mem_avatar" />
            <div className="content">{props.memberInfo.nickname}</div>
        </Box>
    );
};

export default ItemDetailMemberInfo;