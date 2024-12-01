import {Button} from '@mui/material';
import React from 'react';

function Login() {
  return (
    <Button
      onClick={() => {
        window.location.href =
          import.meta.env.VITE_ROOT_ENDPOINT + '/oauth2/authorization/google';
      }}
      variant="contained">
      Login with Google
    </Button>
  );
}

export default Login;
