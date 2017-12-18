const transformCountryLabel = (country) => {
    switch (country) {
        case 'FINLAND':
            return 'Finland';
        case 'CZECH_REPUBLIC':
            return 'Czech Republic';
        case  'SLOVAKIA' :
            return 'Slovakia';
        case 'FRANCE':
            return 'France';
        case 'ENGLAND' :
            return 'England';
        case 'GERMANY' :
            return 'Germany';
        default:
            return 'Uknown team';
    }
}

export default transformCountryLabel;