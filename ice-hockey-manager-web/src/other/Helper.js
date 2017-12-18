export const transformCountryLabel = (country) => {
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
};

export const transformPositionLabel = (position) => {
    switch (position) {
        case 'GOALKEEPER':
            return 'Goalkeeper';
        case 'DEFENSEMAN':
            return 'Defenseman';
        case 'LEFT_WING':
            return 'Left wing';
        case 'CENTER':
            return 'Center';
        case 'RIGHT_WING':
            return 'Right wing';
        default:
            return 'Unknown post';
    }
};