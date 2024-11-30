import {Button, Paper, TextField} from '@mui/material';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import {styled} from '@mui/material/styles';

import React, {useRef, useState} from 'react';
import {POST_SNOWMAN_ENDPOINT} from '../endpoints';

interface Props {
  city: string;
  creds: string;
  refresh: () => void;
  close: () => void;
}

function PostOverlay(props: Props) {
  const [image, setImage] = useState<Blob>();
  const location = useRef<HTMLInputElement>();

  const postSnowman = (locationString: string, image: Blob) => {
    let form = new FormData();
    form.append('image', image);
    form.append('location', locationString);
    form.append('city', props.city);
    fetch(POST_SNOWMAN_ENDPOINT, {
      method: 'POST',
      headers: {Authorization: `Bearer ${props.creds}`},
      body: form,
    }).then(() => {
      setImage(undefined);
      if (location.current) {
        location.current.value = '';
      }
      props.close();
      props.refresh();
    });
  };

  return (
    <Paper
      sx={{
        width: '80%',
        height: '80%',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
      }}>
      <TextField inputRef={location} label="Location" variant="outlined" />
      {image && (
        <img
          style={{width: '50%', height: '30%', objectFit: 'contain'}}
          src={URL.createObjectURL(image as Blob)}></img>
      )}
      <Button
        component="label"
        role={undefined}
        variant="contained"
        tabIndex={-1}
        startIcon={<CloudUploadIcon />}
        sx={{marginTop: 5}}>
        Upload Snowman Photo
        <input
          onChange={e => {
            let files = e.target.files;
            if (files && files.length > 0) {
              let file = files[0];
              let blob = new Blob([file], {type: file.type});
              setImage(blob);
            }
          }}
          type="file"
          hidden
          accept="image/png, image/jpeg"
        />
      </Button>
      <Button
        onClick={() => {
          if (location.current?.value && image) {
            postSnowman(location.current?.value, image);
          } else {
            alert('Please fill out everything');
          }
        }}
        sx={{marginTop: 5}}
        variant="contained">
        Submit
      </Button>
    </Paper>
  );
}

export default PostOverlay;
