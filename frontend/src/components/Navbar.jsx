// @ts-check

import React from 'react';
import { Navbar as BootstrapNavbar, Container, Nav } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

import routes from '../routes.js';

const Navbar = () => {
  const { t } = useTranslation();

  return (
    <BootstrapNavbar bg="dark" variant="gradient" className="mb-3">
      <Container fluid>
        <Nav className="me-auto">
          <Link className="nav-link text-white" to={routes.homePagePath()}>{t('welcomePage')}</Link>
          <Link className="nav-link text-white" to={routes.locationsPagePath()}>{t('locations')}</Link>
        </Nav>
      </Container>
    </BootstrapNavbar>
  );
};

export default Navbar;
