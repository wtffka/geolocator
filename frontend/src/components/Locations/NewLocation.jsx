// @ts-check

import React from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { Form, Button } from 'react-bootstrap';
import { useFormik } from 'formik';
import * as yup from 'yup';
import axios from 'axios';

import { actions as locationsActions } from '../../slices/locationsSlice.js';
import routes from '../../routes.js';

import { useNotify } from '../../hooks/index.js';

const getValidationSchema = () => yup.object().shape({});

const NewLocation = () => {
  const { t } = useTranslation();
  const dispatch = useDispatch();

  const history = useHistory();
  const notify = useNotify();

  const f = useFormik({
    initialValues: {
      location: '',
    },
    validationSchema: getValidationSchema(),
    onSubmit: async (locationData, { setSubmitting, setErrors }) => {
      try {
        const requestLocation = {
          location: locationData.location,
        };
        const { data } = await axios
          .post(routes.addLocation(), requestLocation);
        dispatch(locationsActions.addLocation(data));
        const from = { pathname: routes.locationsPagePath() };
        history.push(from, { message: 'locationAdded' });
      } catch (e) {
        setSubmitting(false);
        if (e.response?.status === 422 && Array.isArray(e.response?.data)) {
          const errors = e.response.data
            .reduce((acc, err) => ({ ...acc, [err.field]: err.defaultMessage }), {});
          setErrors(errors);
          notify.addError('addLocationError');
        }
      }
    },
    validateOnBlur: false,
    validateOnChange: false,
  });

  return (
    <>
      <h1 className="my-4">{t('locationAdding')}</h1>
      <h4 className="my-4">{t('shortAddingLocationTip')}</h4>
      <Form onSubmit={f.handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label htmlFor="location">{t('location')}</Form.Label>
          <Form.Control
            type="text"
            value={f.values.location}
            disabled={f.isSubmitting}
            onChange={f.handleChange}
            onBlur={f.handleBlur}
            id="location"
            name="location"
          />
        </Form.Group>
        <Button variant="primary" type="submit">
          {t('add')}
        </Button>
      </Form>
    </>
  );
};

export default NewLocation;
