import React from "react";
import { Chip, Paper } from "../../../../node_modules/@material-ui/core/index";

const ItemDetailTitle = (props) => {

    const renderDealState = (dealState) => {
        if (dealState === 'APPOINTMENT') return <Chip label="예약중" size="small" style={{marginBottom: 3}} />
        else if (dealState === 'COMPLETE') return <Chip label="거래완료" />
        else return null;
    }

    return (
        // <div className="div_class">
        <Paper className="div_class">
            <div className="label" id="lb_item_title">제목</div>
            <div className="content">
                {renderDealState(props.dealState)} {props.title}<br/>
                <span style={{fontSize: "0.7rem", color: "gray"}}>{props.lastModifiedDate} UPDATE</span>
                {/* {props.dealState} */}
            </div>
        </Paper>
        // </div>
    );
};

export default ItemDetailTitle;