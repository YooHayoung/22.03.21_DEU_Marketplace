import React, { useState } from "react";
import { Box, Button, Container, FormControl, InputLabel, Modal, OutlinedInput, TextField, Typography } from "../../node_modules/@material-ui/core/index";
import { getMyInfo, updateMyNickname } from "../api/Api";
import { UseApi } from "../api/UseApi";
import HeaderContainer from "../containers/HeaderContainer";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '100%',
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 3,
};

const MyPage = ({ token, setToken }) => {
    const [myInfo, setMyInfo] = React.useState({});
    const [modalOpen, setModalOpen] = React.useState(false);
    const [modalNicknameField, setModalNicknameField] = React.useState("");
    const [modalFieldError, setModalFieldError] = React.useState(false);


    const onHandleModalOpen = () => {
        console.log(modalOpen)
        setModalOpen(true);
    };
    const onHandleModalClose = () => {
        setModalOpen(false);
    };

    const onModalNicknameChanged = (e) => {
        if (modalFieldError) {
            setModalFieldError(false);
        }
        setModalNicknameField(e.target.value);
    };

    const afterGetMyInfo = (res) => {
        console.log(res.data.body.result);
        setMyInfo(res.data.body.result);
    };
    React.useEffect(() => {
        (async () => {
            UseApi(getMyInfo, token, setToken, afterGetMyInfo);
        })();
    }, []);

    const renderField = () => {
        return (
            <>
            {/* {renderModal()} */}
            <FormControl fullWidth>
            <TextField
                label={"이름"}
                id="standard-size-normal"
                value={myInfo.name}
                variant="outlined"
                InputProps={{
                    readOnly: true,
                }}
                sx={{mt:2}}
            />
            </FormControl>
            <FormControl fullWidth>
            <TextField
                label={"이메일"}
                id="standard-size-normal"
                value={myInfo.email}
                variant="outlined"
                InputProps={{
                    readOnly: true,
                }}
                sx={{mt:2}}
            />
            </FormControl>
            <FormControl fullWidth>
            <TextField
                label={"닉네임"}
                id="standard-size-normal"
                value={myInfo.nickname}
                variant="outlined"
                InputProps={{
                    readOnly: true,
                }}
                sx={{mt:2}}
            />
            </FormControl>
            <FormControl fullWidth>
            <TextField
                label={"가입일"}
                id="standard-size-normal"
                value={myInfo.createdDate}
                variant="outlined"
                InputProps={{
                    readOnly: true,
                }}
                sx={{mt:2}}
            />
            </FormControl>
            <FormControl fullWidth>
            <TextField
                label={"학생 인증"}
                id="standard-size-normal"
                value={myInfo.stuIdCertificated?"인증 완료":"미인증"}
                variant="outlined"
                InputProps={{
                    readOnly: true,
                }}
                sx={{mt:2}}
            />
            </FormControl>
            </>
        );
    };

    const afterUpdateNickname = (res) => {
        setMyInfo((prev) => ({
            ...prev,
            nickname: modalNicknameField
        }));
        setModalNicknameField('');
        onHandleModalClose();
    };
    const onNicknameUpdateBtnClick = () => {
        if (modalNicknameField != '') {
            (async () => {
                UseApi(updateMyNickname, token, setToken, afterUpdateNickname, {memberNickname: modalNicknameField});
            })();
        } else {
            if (modalFieldError==false) setModalFieldError(true);
        }
    };
    const onModalCloseBtnClick = () => {
        setModalNicknameField('');
        onHandleModalClose();
    };

    const renderModal = () => {
        return (
            <Modal
                open={modalOpen}
                onClose={onHandleModalClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                        닉네임 수정
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 1 }}>
                        <FormControl id="div_newNickname" fullWidth>
                            <TextField 
                                placeholder="새 닉네임을 입력하세요." 
                                variant="standard" 
                                className='new_nickname' 
                                id="new_nickname" 
                                error={modalFieldError}
                                helperText={modalFieldError?"닉네임이 입력되지 않았습니다.":null}
                                onChange={onModalNicknameChanged}
                            />
                        </FormControl>
                        <div style={{marginTop: "1rem", textAlign: "right"}}>
                            <Button onClick={onModalCloseBtnClick}>취소</Button>
                            <Button onClick={onNicknameUpdateBtnClick}>변경</Button>
                        </div>
                    </Typography>
                </Box>
            </Modal>
        );
    }

    return (
        <>
        <HeaderContainer pageName={"마이페이지"} />
        <Container component="div" sx={{mt:2}} >
            {renderModal()}
            {Object.keys(myInfo).length!=0?renderField():null}
            <div style={{minWidth: "15.9rem", marginTop: "0.7rem",marginLeft: "auto",  marginRight: "auto"}}>
                <Button  sx={{m:0.5, p:1}} variant="outlined"s onClick={onHandleModalOpen}>
                    닉네임 수정
                </Button>
                <Button sx={{m:0.5, p:1}} variant="outlined" disabled>
                    학생 인증
                </Button>
                <Button sx={{m:0.5, p:1}} variant="outlined" disabled>
                    회원 탈퇴
                </Button>
            </div>
        </Container>
        </>
    );
};

export default MyPage;