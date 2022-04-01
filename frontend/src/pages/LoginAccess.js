import React, { useEffect, useState } from "react";

const LoginAccess = () => {
   const [accessToken, setAccessToken] = useState('');

   useEffect(() => {
      console.log(window.Headers);
   }, []);

   return (
      <div>
         test
      </div>
   );
};

export default LoginAccess;