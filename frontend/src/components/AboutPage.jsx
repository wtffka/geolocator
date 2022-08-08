import React from 'react';
import { useTranslation } from 'react-i18next';

const AboutPage = () => {
  const { t } = useTranslation();
  return (
    <>
      <h1>{t('about.header')}</h1>
      <br />
      <p>{t('about.firstTip')}</p>
      <p>{t('about.secondTip')}</p>
      <p><li>{t('about.firstSearchWay')}</li></p>
      <p><li>{t('about.secondSearchWay')}</li></p>
    </>
  );
};

export default AboutPage;
